package com.wolfnine.backend.repository;

import com.wolfnine.backend.entity.ShopeeShopConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopeeShopConfigRepository extends JpaRepository<ShopeeShopConfig, Long> {
    List<ShopeeShopConfig> findAllByUserId(long userId);
    Page<ShopeeShopConfig> findAllByUserId(long userId, Pageable pageable);
    Optional<ShopeeShopConfig> findByShopId(long shopId);
}
