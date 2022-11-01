package com.wolfnine.backend.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wolfnine.backend.WolfnineBackendApplication;
import com.wolfnine.backend.entity.Product;
import com.wolfnine.backend.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = WolfnineBackendApplication.class)
class JsonUtilTest {
    @Autowired
    ProductRepository productRepository;

    @Test
    void getItemFromArrayByKey() {
        long productId = 242;
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            JsonParser jsonParser = new JsonParser();
            JsonArray jsonArray = jsonParser.parse(product.getAttributes()).getAsJsonArray();
            JsonObject jsonObject = JsonUtil.getItemFromArrayByKey("title", jsonArray);
            System.out.println(jsonObject.toString());
            System.out.println(jsonObject.get("value"));
        }
    }
}
