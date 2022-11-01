package com.wolfnine.backend.util;

import com.wolfnine.backend.WolfnineBackendApplication;
import com.wolfnine.backend.constant.CrawlConstant;
import com.wolfnine.backend.entity.ShopeeShopConfig;
import com.wolfnine.backend.entity.shopee.ShopeeCredential;
import com.wolfnine.backend.entity.shopee.ShopeeShopInfo;
import com.wolfnine.backend.service.shopeeShopConfig.ShopeeShopConfigService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.*;
import java.net.URL;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WolfnineBackendApplication.class)
class ShopeeUtilTest {
    private static final String accessToken = "4357424649756d62615152616754666d";
    private static final long partnerId = 842499;
    private static final long shopId = 307846426;
    private static final String partnerKey = "0b27ea12b0a4a37084e4f5b81bdfdd02a0d03301f47af6027511fa8bc253a05f";
    private static final String code = "4e685954476e45514a41435862647564";
    @Autowired
    private ShopeeShopConfigService shopeeShopConfigService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shopAuth() {
        ShopeeUtil.shopAuth("https://google.com");
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
        ShopeeUtil.getProductCategoryList(accessToken, partnerId, shopId, partnerKey, 10, 1);
    }

    @Test
    void getLogisticChannelList() {
        ShopeeUtil.getLogisticChannelList(accessToken, partnerId, shopId, partnerKey);
    }

    @Test
    void uploadImage() throws IOException {
        String filePath = FileUtil.saveImageWithUrl("https://image.shutterstock.com/image-photo/prairie-grasses-twilight-260nw-1267832863.jpg");
        String imageId = ShopeeUtil.uploadImage(partnerId, partnerKey, filePath);
        System.out.println("Image ID: " + imageId);
        assertNotNull(imageId);
    }

    @Test
    void getBrandList() {
        ShopeeUtil.getBrandList(accessToken, partnerId, shopId, partnerKey, 100, 10, 1);
    }

    @Test
    void getShopInfo() throws ParseException, IOException {
        ShopeeShopInfo shopeeShopInfo = ShopeeUtil.getShopInfo(accessToken, partnerId, shopId, partnerKey);
        assertNotNull(shopeeShopInfo.getShop_name());
    }

    @Test
    void uploadImageV2() throws IOException {
        URL url = new URL("https://image.shutterstock.com/image-photo/prairie-grasses-twilight-260nw-1267832863.jpg");
        BufferedImage image = ImageIO.read(url);
        ShopeeUtil.uploadImageV2(partnerId, partnerKey, image);
    }

    @Test
    void getRefreshTokenShopLevel() throws ParseException, IOException {
        ShopeeShopConfig shopeeShopConfig = shopeeShopConfigService.findById(9);
        if(shopeeShopConfig != null) {
            ShopeeCredential shopeeCredential = ShopeeUtil.getRefreshTokenShopLevel(
                    shopeeShopConfig.getRefreshToken(),
                    partnerId,
                    partnerKey,
                    shopeeShopConfig.getShopId()
            );
            System.out.println(shopeeCredential.getAccess_token());
            assertNotNull(shopeeCredential.getAccess_token());
        }
    }
}
