package com.dinesh.ecom.service;

import com.dinesh.ecom.entity.CartItem;
import com.dinesh.ecom.entity.Customer;
import com.dinesh.ecom.entity.Order;
import com.dinesh.ecom.entity.OrderItem;
import com.dinesh.ecom.entity.Product;
import com.dinesh.ecom.reposistory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CartItem> getCartItems(Long customerId) {
        return cartItemRepository.findByCustomerId(customerId);
    }

    @Override
    @Transactional
    public void addToCart(Long customerId, Long productId, int quantity) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        Product product = productRepository.findById(productId).orElse(null);

        if (customer != null && product != null) {
            CartItem cartItem = new CartItem();
            cartItem.setCustomer(customer);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }
    }

    @Override
    @Transactional
    public boolean placeOrder(Long customerId, String address) {
        List<CartItem> cartItems = cartItemRepository.findByCustomerId(customerId);

        if (cartItems.isEmpty()) {
            return false;
        }

        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            return false;
        }

        Order order = new Order();
        order.setAddress(address);
        order.setCustomer(customer);
        order = orderRepository.save(order);

        for (CartItem item : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItemRepository.save(orderItem);
        }

        cartItemRepository.deleteByCustomerId(customerId);
        return true;
    }
}
