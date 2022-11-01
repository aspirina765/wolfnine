package com.wolfnine.backend.restApi.client;

import com.wolfnine.backend.entity.dto.shopeeShopConfig.SaveShopeeShopConfig;
import com.wolfnine.backend.service.shopeeShopConfig.ShopeeShopConfigService;
import com.wolfnine.backend.util.ResponseHandler;
import com.wolfnine.backend.util.ShopeeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping(path = "/api/v1/shopeeShopConfigs")
@RequiredArgsConstructor
public class ShopeeShopConfigController {
    private final ShopeeShopConfigService shopeeShopConfigService;

    @GetMapping("/authUrl")
    public ResponseEntity<?> generateShopAuthUrl(@RequestParam String redirectUrl) {
        return ResponseHandler.generateResponse(ShopeeUtil.shopAuth(redirectUrl));
    }

    @GetMapping
    public ResponseEntity<?> findAllByAuthUser(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ) {
        return ResponseHandler.generateResponse(shopeeShopConfigService.findAllByAuthUser(limit, page, sortBy));
    }

    @PostMapping
    public ResponseEntity<?> save( @Valid @RequestBody SaveShopeeShopConfig saveShopeeShopConfig) throws ParseException, IOException {
        return ResponseHandler.generateResponse(shopeeShopConfigService.save(saveShopeeShopConfig));
    }

    @GetMapping("/{id}/categories")
    public ResponseEntity<?> getProductCategoryList(
            @PathVariable long id,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit
    ) throws ParseException, IOException {
        return ResponseHandler.generateResponse(shopeeShopConfigService.getShopeeCategoryList(id, page, limit ));
    }

    @GetMapping("/{id}/logisticChannels")
    public ResponseEntity<?> getLogisticList(@PathVariable long id) {
        return ResponseHandler.generateResponse(shopeeShopConfigService.getShopeeLogisticChannelList(id));
    }

    @GetMapping("/{id}/brands")
    public ResponseEntity<?> getBrandList(
            @PathVariable long id,
            @RequestParam int shopeeCategoryId,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "1") int offset
    ) {
        return ResponseHandler.generateResponse(shopeeShopConfigService.getShopeeBrandList(id, shopeeCategoryId, pageSize, offset));
    }
}
