package com.wolfnine.backend.service.product;

import com.wolfnine.backend.WolfnineBackendApplication;
import com.wolfnine.backend.entity.Product;
import com.wolfnine.backend.entity.dto.shopeeShopConfig.PushProductToShopeeShop;
import com.wolfnine.backend.entity.entityEnum.ProductStatus;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WolfnineBackendApplication.class)
class ProductServiceV1Test {
    @Autowired
    private ProductService productService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findAllByStatus() {
        List<Product> products = productService.findAllByStatus(ProductStatus.PENDING);
        for(Product product : products) {
            System.out.println(product.getCrawlCategory());
        }
    }

    @Test
    void pushProductToShopeeShop() {
        List<Long> productIds = new ArrayList<>();
        productIds.add(Long.parseLong("242"));
        PushProductToShopeeShop pushProductToShopeeShop = PushProductToShopeeShop.builder()
                .productIds(productIds)
                .categoryId(100419)
                .logisticId(5001)
                .build();
        boolean result = productService.pushProductToShopeeShop(pushProductToShopeeShop);
        assertTrue(result);
    }
}
