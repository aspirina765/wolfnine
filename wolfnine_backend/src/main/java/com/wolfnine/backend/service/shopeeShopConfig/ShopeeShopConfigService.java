package com.wolfnine.backend.service.shopeeShopConfig;

import com.wolfnine.backend.entity.ShopeeShopConfig;
import com.wolfnine.backend.entity.dto.shopeeShopConfig.SaveShopeeShopConfig;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShopeeShopConfigService {
    List<ShopeeShopConfig> findAll();
    List<ShopeeShopConfig> findAllByUserId();
    ShopeeShopConfig save(SaveShopeeShopConfig saveShopeeShopConfig);
    ShopeeShopConfig findById(long id);
}
