package com.dinesh.ecom.controllers;

import com.dinesh.ecom.entity.Admin;
import com.dinesh.ecom.entity.Product;
import com.dinesh.ecom.service.AdminService;
import com.dinesh.ecom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ProductService productService;

    @GetMapping("/admin/register")
    public String registerAdmin() {
        return "admin/register";
    }

    @PostMapping("/admin/register")
    public String registerAdmin(@RequestParam String username, @RequestParam String password, Model model) {
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);
        adminService.register(admin);
        model.addAttribute("msg", "Admin registration successful!");
        return "admin/login";
    }

    @GetMapping("/admin/login")
    public String loginAdmin() {
        return "admin/login";
    }

    @PostMapping("/admin/login")
    public String loginAdmin(@RequestParam String username, @RequestParam String password, Model model) {
        Admin admin = adminService.findByUsername(username);
        if (admin != null && admin.getPassword().equals(password)) {
            model.addAttribute("products", productService.getAllProducts());
            return "admin/home";
        } else {
            model.addAttribute("msg", "Invalid username or password!");
            return "admin/login";
        }
    }

    @GetMapping("/admin/products")
    public String viewProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin/products";
    }

    @GetMapping("/admin/add-product")
    public String addProductForm() {
        return "admin/add-product";
    }

    @PostMapping("/admin/add-product")
    public String addProduct(@RequestParam String name, @RequestParam String price, @RequestParam String imageUrl, Model model) {
        BigDecimal priceDecimal;
        try {
            priceDecimal = new BigDecimal(price);
        } catch (NumberFormatException e) {
            model.addAttribute("msg", "Invalid price format!");
            return "admin/add-product";
        }

        Product product = new Product();
        product.setName(name);
        product.setPrice(priceDecimal);
        product.setImageUrl(imageUrl);
        productService.saveProduct(product);
        model.addAttribute("msg", "Product added successfully!");
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/delete-product")
    public String deleteProduct(@RequestParam Long id, Model model) {
        productService.deleteProduct(id);
        model.addAttribute("msg", "Product deleted successfully!");
        return "redirect:/admin/products";
    }
}
