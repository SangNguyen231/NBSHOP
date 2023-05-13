package com.springboot.nbshop.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.nbshop.dto.ProductDTO;
import com.springboot.nbshop.dto.UserDTO;
import com.springboot.nbshop.entity.Category;
import com.springboot.nbshop.entity.Order;
import com.springboot.nbshop.entity.OrderDetail;
import com.springboot.nbshop.entity.Product;
import com.springboot.nbshop.entity.Role;
import com.springboot.nbshop.entity.User;
import com.springboot.nbshop.service.CategoryService;
import com.springboot.nbshop.service.OrderDetailService;
import com.springboot.nbshop.service.OrderService;
import com.springboot.nbshop.service.ProductService;
import com.springboot.nbshop.service.RoleService;
import com.springboot.nbshop.service.UserService;

@Controller
public class AdminController {
	public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;

	@Autowired
	CategoryService categoryService;

	@Autowired
	ProductService productService;

	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	@Autowired
	OrderService orderService;

	@Autowired
	OrderDetailService orderDetailService;

	@GetMapping("/admin")
	public String adminHome() {
		return "adminHome";
	}

	@GetMapping("/admin/users")
	public String getAcc(Model model) {
		model.addAttribute("users", userService.getAllUser());
		return "users";
	}

	@GetMapping("/admin/users/add")
	public String getUserAdd(Model model) {
		model.addAttribute("userDTO", new UserDTO());
		model.addAttribute("roles", roleService.getAllRole());
		return "usersAdd";
	}

	@PostMapping("/admin/users/add")
	public String postUserAdd(@ModelAttribute("userDTO") UserDTO userDTO) {
		User user = new User();
		user.setId(userDTO.getId());
		user.setEmail(userDTO.getEmail());
		user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		List<Role> roles = new ArrayList<>();
		for (Integer item : userDTO.getRoleIds()) {
			roles.add(roleService.findRoleById(item).get());
		}
		user.setRoles(roles);
		userService.updateUser(user);
		return "redirect:/admin/users";
	}

	@GetMapping("/admin/users/delete/{id}")
	public String deleteUser(@PathVariable int id) {
		userService.removeUserById(id);
		return "redirect:/admin/users";
	}

	@GetMapping("/admin/users/update/{id}")
	public String updateUser(@PathVariable int id, Model model) {
		Optional<User> opUser = userService.getUserById(id);
		if (opUser.isPresent()) {
			User user = opUser.get();
			UserDTO userDTO = new UserDTO();
			userDTO.setId(user.getId());
			userDTO.setEmail(user.getEmail());
			userDTO.setPassword("");
			userDTO.setFirstName(user.getFirstName());
			userDTO.setLastName(user.getLastName());
			List<Integer> roleIds = new ArrayList<>();
			for (Role item : user.getRoles()) {
				roleIds.add(item.getId());
			}
			model.addAttribute("userDTO", userDTO);
			model.addAttribute("roles", roleService.getAllRole());
			return "usersAdd";
		} else {
			return "404";
		}

	}

	@GetMapping("/admin/categories")
	public String getCat(Model model) {
		model.addAttribute("categories", categoryService.getAllCategory());
		return "categories";
	}

	@GetMapping("/admin/categories/add")
	public String getCatAdd(Model model) {
		model.addAttribute("category", new Category());
		return "categoriesAdd";
	}

	@PostMapping("/admin/categories/add")
	public String postCatAdd(@ModelAttribute("category") Category category) {
		categoryService.updateCategory(category);
		return "redirect:/admin/categories";
	}

	@GetMapping("/admin/categories/delete/{id}")
	public String deleteCat(@PathVariable int id) {
		categoryService.removeCategoryById(id);
		return "redirect:/admin/categories";
	}

	@GetMapping("/admin/categories/update/{id}")
	public String updateCat(@PathVariable int id, Model model) {
		Optional<Category> category = categoryService.getCategoryById(id);
		if (category.isPresent()) {
			model.addAttribute("category", category.get());
			return "categoriesAdd";
		} else {
			return "404";
		}
	}

	@GetMapping("/admin/products")
	public String getPro(Model model) {
		model.addAttribute("products", productService.getAllProduct());
		return "products";
	}

	@GetMapping("/admin/products/add")
	public String getProAdd(Model model) {
		model.addAttribute("productDTO", new ProductDTO());
		model.addAttribute("categories", categoryService.getAllCategory());
		return "productsAdd";
	}

	@PostMapping("/admin/products/add")
	public String postProAdd(@ModelAttribute("productDTO") ProductDTO productDTO,
			@RequestParam("productImage") MultipartFile fileProductImage, @RequestParam("imgName") String imgName)
			throws IOException {
		Product product = new Product();
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
		product.setPrice(productDTO.getPrice());
		product.setWeight(productDTO.getWeight());
		product.setDescription(productDTO.getDescription());
		String imageUUID;
		if (!fileProductImage.isEmpty()) {
			imageUUID = fileProductImage.getOriginalFilename();
			Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
			Files.write(fileNameAndPath, fileProductImage.getBytes());
		} else {
			imageUUID = imgName;
		}
		product.setImageName(imageUUID);
		productService.updateProduct(product);
		return "redirect:/admin/products";
	}

	@GetMapping("/admin/products/delete/{id}")
	public String deletePro(@PathVariable long id) {
		productService.removeProductById(id);
		return "redirect:/admin/products";
	}

	@GetMapping("/admin/products/update/{id}")
	public String updatePro(@PathVariable long id, Model model) {
		Optional<Product> opProduct = productService.getProductById(id);
		if (opProduct.isPresent()) {
			Product product = opProduct.get();
			ProductDTO productDTO = new ProductDTO();
			productDTO.setId(product.getId());
			productDTO.setName(product.getName());
			productDTO.setCategoryId(product.getCategory().getId());
			productDTO.setPrice(product.getPrice());
			productDTO.setWeight(product.getWeight());
			productDTO.setDescription(product.getDescription());
			productDTO.setImageName(product.getImageName());
			model.addAttribute("productDTO", productDTO);
			model.addAttribute("categories", categoryService.getAllCategory());
			return "productsAdd";
		} else {
			return "404";
		}

	}

	@GetMapping("/admin/order")
	public String getOrder(Model model) {
		model.addAttribute("orders", orderService.getAllOrder());
		return "order";
	}

	@GetMapping("/order/{id}")
	public String getOrderDetail(@PathVariable Integer id, Model model) {
		model.addAttribute("orderdetails", orderDetailService.getOrder(id));
		return "orderdetail";
	}

	@PostMapping("/order/add")
	public String addOrder(Model model, @RequestParam(name = "phone") String phone,
			@RequestParam(name = "address") String address, HttpSession session) {
		List<Product> carts = (List<Product>) session.getAttribute("cart");
		if (carts == null) {
			return "redirect:/";
		}
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String currentUsername = ((UserDetails) principal).getUsername();

		Order order = new Order();
		order.setUser(userService.getUserByEmail(currentUsername).get());
		order.setAddress(address.trim());
		order.setPhone(phone.trim());
		order.setDate(new Date(System.currentTimeMillis()));
		orderService.updateOrder(order);

		List<OrderDetail> detailList = new ArrayList<>();
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setOrder(order);
		orderDetail.setProduct(carts.get(0));
		orderDetail.setCount(demsolan(carts.get(0), carts));
		detailList.add(orderDetail);

		int i = 1;
		for (i = 1; i < carts.size(); i++) {
			if (!ktTrung(carts.get(i), detailList)) {
				OrderDetail orderDetail2 = new OrderDetail();
				orderDetail2.setOrder(order);
				orderDetail2.setProduct(carts.get(i));
				orderDetail2.setCount(demsolan(carts.get(i), carts));
				detailList.add(orderDetail2);
			}
		}
		detailList.forEach(we -> {
			orderDetailService.updateOrderDetail(we);
		});
		session.removeAttribute("cart");
		return "redirect:/";

	}

	public int demsolan(Product pd, List<Product> list) {
		AtomicInteger dem = new AtomicInteger();
		list.forEach(n -> {
			if (pd.getId() == n.getId()) {
				dem.getAndIncrement();
			}
		});
		return dem.get();
	}

	public boolean ktTrung(Product pd, List<OrderDetail> detailList) {
		AtomicBoolean rt = new AtomicBoolean(false);
		detailList.forEach(n -> {
			if (pd.getId() == n.getProduct().getId()) {
				rt.set(true);
			}
		});
		return rt.get();
	}
}
