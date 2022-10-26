package com.wolfnine.backend.service.shopeeShopConfig;

import com.wolfnine.backend.entity.ShopeeShopConfig;
import com.wolfnine.backend.entity.dto.shopeeShopConfig.SaveShopeeShopConfig;
import com.wolfnine.backend.repository.ShopeShopConfigRepository;
import com.wolfnine.backend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopeeShopConfigServiceV1 implements ShopeeShopConfigService{
    private final ShopeShopConfigRepository shopeShopConfigRepository;
    private final UserService userService;
    private final Environment environment;

    @Override
    public List<ShopeeShopConfig> findAll() {
        return shopeShopConfigRepository.findAll();
    }

    @Override
    public List<ShopeeShopConfig> findAllByUserId() {
        return shopeShopConfigRepository.findAllByUserId(userService.findByAuthUser().getId());
    }

    @Override
    public ShopeeShopConfig save(SaveShopeeShopConfig saveShopeeShopConfig) {
        saveShopeeShopConfig.setUserId(userService.findByAuthUser().getId());
        environment.getProperty("SHOPEE_PARTNER_ID");
        return shopeShopConfigRepository.save(saveShopeeShopConfig.toShopeeShopConfig());
    }

    @Override
    public ShopeeShopConfig findById(long id) {
        Optional<ShopeeShopConfig> optionalShopeeShopConfig = shopeShopConfigRepository.findById(id);
        if(optionalShopeeShopConfig.isPresent()) {
            return optionalShopeeShopConfig.get();
        }
        return null;
    }
}
