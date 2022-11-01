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

import java.io.IOException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WolfnineBackendApplication.class)
class ShopeeShopConfigServiceV1Test {
    @Autowired
    private ShopeeShopConfigService shopeeShopConfigService;
    @Autowired
    private Environment environment;

    @Test
    void save() throws ParseException, IOException {
        SaveShopeeShopConfig saveShopeeShopConfig = SaveShopeeShopConfig.builder()
                .code("564a6973757a486a4c5a4b704370654a")
                .shopId(307846426)
                .build();
        shopeeShopConfigService.save(saveShopeeShopConfig);
    }
}
