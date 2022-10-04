package com.wolfnine.backend.service.product;

import com.wolfnine.backend.entity.Product;
import com.wolfnine.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceV1 implements ProductService{
    final ProductRepository productRepository;

    @Override
    public List<Product> findByUserId(long userId) {
        return productRepository.findAllByUserId(userId);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> saveAll(List<Product> products) {
        return productRepository.saveAll(products);
    }
}
