package com.wolfnine.backend.service.product;

import com.wolfnine.backend.entity.Product;
import com.wolfnine.backend.entity.dto.ProductDto;
import com.wolfnine.backend.entity.entityEnum.ProductStatus;
import com.wolfnine.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceV1 implements ProductService{
    final ProductRepository productRepository;

    @Override
    public List<ProductDto> findByUserId(long userId) {
        List<Product> products = productRepository.findAllByUserId(userId);
        List<ProductDto> productDtos = products.stream()
                .map(product -> product.toProductDto())
                .collect(Collectors.toList());
        return productDtos;
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> saveAll(List<Product> products) {
        return productRepository.saveAll(products);
    }

    @Override
    public List<Product> findAllByStatus(ProductStatus status) {
        return productRepository.findAllByStatus(status);
    }

    @Override
    public Product update(long id, Product product) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()) {
            return productRepository.save(product);
        }
        return null;
    }
}
