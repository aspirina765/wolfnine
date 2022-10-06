package com.wolfnine.backend.service.product;

import com.wolfnine.backend.entity.Product;
import com.wolfnine.backend.entity.dto.ProductDto;
import com.wolfnine.backend.entity.entityEnum.ProductStatus;

import java.util.List;

public interface ProductService {
    List<ProductDto> findByUserId(long userId);
    Product save(Product product);
    List<Product> saveAll(List<Product> products);
    List<Product> findAllByStatus(ProductStatus status);
    Product update(long id, Product product);
}
