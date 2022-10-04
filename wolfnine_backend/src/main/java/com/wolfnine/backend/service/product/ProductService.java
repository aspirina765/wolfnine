package com.wolfnine.backend.service.product;

import com.wolfnine.backend.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findByUserId(long userId);
    Product save(Product product);
    List<Product> saveAll(List<Product> products);
}
