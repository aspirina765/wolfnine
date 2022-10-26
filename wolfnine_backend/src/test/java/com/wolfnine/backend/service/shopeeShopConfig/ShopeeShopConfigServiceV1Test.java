package com.wolfnine.backend.service.shopeeShopConfig;

import com.wolfnine.backend.WolfnineBackendApplication;
import com.wolfnine.backend.entity.dto.shopeeShopConfig.SaveShopeeShopConfig;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WolfnineBackendApplication.class)
class ShopeeShopConfigServiceV1Test {
    @Autowired
    private ShopeeShopConfigService shopeeShopConfigService;
    @Autowired
    private Environment environment;

    @Test
    void save() {
        System.out.println(".ENV " + environment.getProperty("SHOPEE_PARTNER_KEY"));
//        SaveShopeeShopConfig saveShopeeShopConfig = SaveShopeeShopConfig.builder()
//                .code("787455")
//                .shopId(478780)
//                .build();
//        shopeeShopConfigService.save();
    }
}
