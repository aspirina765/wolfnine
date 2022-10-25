package com.wolfnine.backend.util;

import com.wolfnine.backend.WolfnineBackendApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WolfnineBackendApplication.class)
class ShopeeUtilTest {
    private static final String accessToken = "5a49656d50586d615645756d4f467358";
    private static final long partnerId = 842499;
    private static final long shopId = 307846426;
    private static final String partnerKey = "0b27ea12b0a4a37084e4f5b81bdfdd02a0d03301f47af6027511fa8bc253a05f";
    private static final String code = "4e685954476e45514a41435862647564";

    @BeforeEach
    void setUp() {
    }

    @Test
    void shopAuth() {
        ShopeeUtil.shopAuth();
    }

    @Test
    void getTokenShopLevel() throws ParseException, IOException {
        ShopeeUtil.getTokenShopLevel(code, partnerId, partnerKey, shopId);
    }

    @Test
    void addProductItem() {
        ShopeeUtil.addProductItem(accessToken, partnerId, shopId, partnerKey);
    }

    @Test
    void getShopCategoryList() {
        ShopeeUtil.getShopCategoryList(accessToken, partnerId, shopId, partnerKey);
    }

    @Test
    void addShopCategory() {
        ShopeeUtil.addShopCategory(accessToken, partnerId, shopId, partnerKey);
    }

    @Test
    void getProductCategoryList() {
        ShopeeUtil.getProductCategoryList(accessToken, partnerId, shopId, partnerKey);
    }

    @Test
    void getLogisticChannelList() {
        ShopeeUtil.getLogisticChannelList(accessToken, partnerId, shopId, partnerKey);
    }

    @Test
    void uploadImage() {
        ShopeeUtil.uploadImage(partnerId, partnerKey);
    }

    @Test
    void getBrandList() {
        ShopeeUtil.getBrandList(accessToken, partnerId, shopId, partnerKey);
    }
}
