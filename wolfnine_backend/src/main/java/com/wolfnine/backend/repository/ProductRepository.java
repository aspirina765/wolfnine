package com.wolfnine.backend.repository;

import com.wolfnine.backend.entity.Product;
import com.wolfnine.backend.entity.entityEnum.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByUserId(long userId);
    List<Product> findAllByStatus(ProductStatus status);
}
