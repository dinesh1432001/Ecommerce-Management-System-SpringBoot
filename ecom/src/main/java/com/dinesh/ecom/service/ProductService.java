package com.dinesh.ecom.service;

import com.dinesh.ecom.entity.Product;
import com.dinesh.ecom.reposistory.CartItemRepository;
import com.dinesh.ecom.reposistory.OrderItemRepository;
import com.dinesh.ecom.reposistory.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Transactional
    public void deleteProduct(Long productId) {
        
        orderItemRepository.deleteByProductId(productId);
        
        
        // Now delete the product
        productRepository.deleteById(productId);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
