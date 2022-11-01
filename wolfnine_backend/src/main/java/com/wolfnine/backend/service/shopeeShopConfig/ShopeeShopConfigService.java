package com.wolfnine.backend.service.shopeeShopConfig;

import com.wolfnine.backend.entity.ShopeeShopConfig;
import com.wolfnine.backend.entity.dto.shopeeShopConfig.SaveShopeeShopConfig;
import com.wolfnine.backend.entity.shopee.ShopeeCategory;
import com.wolfnine.backend.entity.shopee.ShopeeLogisticChannel;
import com.wolfnine.backend.entity.shopee.brand.ShopeeBrand;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Service
public interface ShopeeShopConfigService {
    List<ShopeeShopConfig> findAll();
    List<ShopeeShopConfig> findAllByAuthUser();
    Page<ShopeeShopConfig> findAllByAuthUser(int limit, int page, String sortBy);
    ShopeeShopConfig save(SaveShopeeShopConfig saveShopeeShopConfig) throws ParseException, IOException;
    ShopeeShopConfig findById(long id);
    List<ShopeeCategory> getShopeeCategoryList(long shopeeShopConfigId, int page, int limit);
    List<ShopeeLogisticChannel> getShopeeLogisticChannelList(long shopeeShopConfigId);
    List<ShopeeBrand> getShopeeBrandList(long shopeeShopConfigId, int shopeeCategoryId, int pageSize, int offset);
    ShopeeShopConfig refreshAccessTokenShopeeShop(ShopeeShopConfig shopeeShopConfig);
}
