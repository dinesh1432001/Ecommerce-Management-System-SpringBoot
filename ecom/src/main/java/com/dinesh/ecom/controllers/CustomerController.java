package com.dinesh.ecom.controllers;

import com.dinesh.ecom.entity.Customer;
import com.dinesh.ecom.service.CustomerService;
import com.dinesh.ecom.service.ProductService;
import com.dinesh.ecom.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @GetMapping("/register")
    public String register() {
        return "customer/register";
    }

    @PostMapping("/register")
    public String registerCustomer(@RequestParam String username, @RequestParam String password, @RequestParam String address, Model model) {
        Customer customer = new Customer();
        customer.setUsername(username);
        customer.setPassword(password);
        customer.setAddress(address);
        customerService.register(customer);
        model.addAttribute("msg", "Registration successful!");
        return "customer/login";
    }

    @GetMapping("/login")
    public String login() {
        return "customer/login";
    }

    @PostMapping("/login")
    public String loginCustomer(@RequestParam String username, @RequestParam String password, Model model) {
        Customer customer = customerService.findByUsername(username);
        if (customer != null && customer.getPassword().equals(password)) {
            model.addAttribute("customer", customer);
            model.addAttribute("products", productService.getAllProducts());
            return "customer/home";
        } else {
            model.addAttribute("msg", "Invalid username or password!");
            return "customer/login";
        }
    }

    @GetMapping("/home")
    public String homePage(@RequestParam Long customerId, Model model) {
        Customer customer = customerService.findById(customerId);
        if (customer != null) {
            model.addAttribute("customer", customer);
            model.addAttribute("products", productService.getAllProducts());
            model.addAttribute("msg", "Welcome to the home page!");
            return "customer/home";
        } else {
            model.addAttribute("msg", "Customer not found!");
            return "customer/login";
        }
    }

    @GetMapping("/cart")
    public String viewCart(@RequestParam Long customerId, Model model) {
        model.addAttribute("cartItems", cartService.getCartItems(customerId));
        model.addAttribute("customer", customerService.findById(customerId));
        return "customer/cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long customerId, @RequestParam Long productId, @RequestParam int quantity) {
        cartService.addToCart(customerId, productId, quantity);
        return "redirect:/cart?customerId=" + customerId;
    }

    @PostMapping("/order")
    public String placeOrder(@RequestParam Long customerId, @RequestParam String address, Model model) {
        boolean isOrderPlaced = cartService.placeOrder(customerId, address);
        model.addAttribute("customer", customerService.findById(customerId));
        if (isOrderPlaced) {
            model.addAttribute("msg", "Order placed successfully!");
        } else {
            model.addAttribute("msg", "Failed to place the order.");
        }
        return "customer/home";
    }
}
