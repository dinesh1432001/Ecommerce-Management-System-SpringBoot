package com.dinesh.ecom.service;

import com.dinesh.ecom.entity.Product;
import com.dinesh.ecom.reposistory.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl extends ProductService {

    @Autowired
    private ProductRepository productRepository;

    
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
