package com.dinesh.ecom.service;

import com.dinesh.ecom.entity.CartItem;

import java.util.List;

public interface CartService {
    List<CartItem> getCartItems(Long customerId);
    void addToCart(Long customerId, Long productId, int quantity);
    boolean placeOrder(Long customerId, String address);
}
