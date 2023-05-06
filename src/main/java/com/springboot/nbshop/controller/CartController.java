package com.springboot.nbshop.controller;


import com.springboot.nbshop.entity.Product;
import com.springboot.nbshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CartController {
    @Autowired
    ProductService productService;

    @GetMapping("/cart")
    public String cartGet(Model model,HttpSession session){
        List<Product> carts = (List<Product>) session.getAttribute("cart");
        if(carts==null){
            carts= new ArrayList<>();
        }
        model.addAttribute("cartCount", carts.size());
        model.addAttribute("total", carts.stream().mapToDouble(Product::getPrice).sum());
        model.addAttribute("cart", carts);
        return "cart";
    }//page cart

    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable int id, HttpSession session){
        List<Product> carts = (List<Product>) session.getAttribute("cart");

        if(carts==null){
            carts= new ArrayList<>();
        }

        Optional<Product> pd = productService.getProductById(id);
        carts.add(pd.get());

        session.setAttribute("cart",carts);
        return "redirect:/shop";
    }//click add from page viewProduct

    @GetMapping("/cart/removeItem/{index}")
    public String cartItemRemove(@PathVariable int index,HttpSession session){
        List<Product> carts = (List<Product>) session.getAttribute("cart");
        if(carts==null){
            carts= new ArrayList<>();
        }
        carts.remove(index);
        return "redirect:/cart";
    } // delete 1 product

    @GetMapping("/checkout")
    public String checkout(Model model,HttpSession session){
        List<Product> carts = (List<Product>) session.getAttribute("cart");
        if(carts==null){
            carts= new ArrayList<>();
        }
        model.addAttribute("cartCount", carts.size());
        model.addAttribute("total", carts.stream().mapToDouble(Product::getPrice).sum());
        return "checkout";
    } // checkout totalPrice

}
