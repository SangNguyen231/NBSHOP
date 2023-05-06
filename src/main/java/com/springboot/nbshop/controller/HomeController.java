package com.springboot.nbshop.controller;


import com.springboot.nbshop.dto.UserDTO;
import com.springboot.nbshop.entity.Order;
import com.springboot.nbshop.entity.Product;
import com.springboot.nbshop.entity.Role;
import com.springboot.nbshop.entity.User;
import com.springboot.nbshop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;

    @GetMapping({"/search"})
    public String searchProduct(@RequestParam String name, Model model,HttpSession session){
        List<Product> carts = (List<Product>) session.getAttribute("cart");
        if(carts==null){
            carts= new ArrayList<>();
        }
        model.addAttribute("cartCount", carts.size());
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("products", productService.finProductsByProductName(name.trim()));
        System.out.println(name);
        return "shop";
    }

    @GetMapping("/users/add")
    public String updateUser(Model model, HttpSession session){
        List<Product> carts = (List<Product>) session.getAttribute("cart");
        if(carts==null){
            carts= new ArrayList<>();
        }


        UserDTO currentUser = new UserDTO();
        List<Order> orderList = new ArrayList<>();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails && ((UserDetails) principal).getUsername() != null) {
            String currentUsername = ((UserDetails)principal).getUsername();
            User user = userService.getUserByEmail(currentUsername).get();
            currentUser.setId(user.getId());
            currentUser.setEmail(user.getEmail());
            currentUser.setPassword("");
            currentUser.setFirstName(user.getFirstName());
            currentUser.setLastName(user.getLastName());
            List<Integer> roleIds = new ArrayList<>();
            for (Role item:user.getRoles()) {
                roleIds.add(item.getId());
            }
            currentUser.setRoleIds(roleIds);

            orderList = orderService.getOrderByUser(user.getId());



        }//get current User runtime
        model.addAttribute("cartCount", carts.size());
        model.addAttribute("orders", orderList);
        model.addAttribute("userDTO", currentUser);
        return "userRoleAdd";
    }
    @PostMapping("/users/add")
    public String postUserAdd(@ModelAttribute("userDTO") UserDTO userDTO) {
        //convert dto > entity
        User user = new User();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        List<Role> roles = userService.getUserById(user.getId()).get().getRoles();
        user.setRoles(roles);

        userService.updateUser(user);
        return "redirect:/";
    }

    @GetMapping({"/", "/home","/shop"})
    public String shop(Model model, HttpSession session){
        List<Product> carts = (List<Product>) session.getAttribute("cart");
        if(carts==null){
            carts= new ArrayList<>();
        }
        model.addAttribute("cartCount", carts.size());
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("products", productService.getAllProduct());
        return "shop";
    } //view All Products

    @GetMapping("/shop/category/{id}")
    public String shopByCat(@PathVariable int id, Model model,HttpSession session){
        List<Product> carts = (List<Product>) session.getAttribute("cart");
        if(carts==null){
            carts= new ArrayList<>();
        }
        model.addAttribute("cartCount", carts.size());
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("products", productService.getAllProductByCategoryId(id));
        return "shop";
    } //view Products By Category

    @GetMapping("/shop/viewproduct/{id}")
    public String viewProduct(@PathVariable long id, Model model,HttpSession session){
        List<Product> carts = (List<Product>) session.getAttribute("cart");
        if(carts==null){
            carts= new ArrayList<>();
        }
        model.addAttribute("cartCount", carts.size());
        model.addAttribute("product", productService.getProductById(id).get());
        return "viewProduct";
    } //view product Details

}
