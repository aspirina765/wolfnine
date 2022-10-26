package com.wolfnine.backend.restApi.client;

import com.wolfnine.backend.entity.dto.shopeeShopConfig.SaveShopeeShopConfig;
import com.wolfnine.backend.service.shopeeShopConfig.ShopeeShopConfigService;
import com.wolfnine.backend.util.ResponseHandler;
import com.wolfnine.backend.util.ShopeeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/shopeeShopConfigs")
@RequiredArgsConstructor
public class ShopeeShopConfigController {
    private final ShopeeShopConfigService shopeeShopConfigService;

    @GetMapping("/authUrl")
    public ResponseEntity<?> generateShopAuthUrl() {
        return ResponseHandler.generateResponse(ShopeeUtil.shopAuth());
    }

    @PostMapping
    public ResponseEntity<?> save( @Valid @RequestBody SaveShopeeShopConfig saveShopeeShopConfig) {
        return ResponseHandler.generateResponse(shopeeShopConfigService.save(saveShopeeShopConfig));
    }
}
