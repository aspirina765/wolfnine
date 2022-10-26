package com.wolfnine.backend.repository;

import com.wolfnine.backend.entity.ShopeeShopConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopeShopConfigRepository extends JpaRepository<ShopeeShopConfig, Long> {
    List<ShopeeShopConfig> findAllByUserId(long userId);
}
