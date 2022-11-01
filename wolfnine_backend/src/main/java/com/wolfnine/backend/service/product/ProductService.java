package com.wolfnine.backend.service.product;

import com.wolfnine.backend.entity.Product;
import com.wolfnine.backend.entity.dto.ProductDto;
import com.wolfnine.backend.entity.dto.pushProductApiConfig.PushProductToApi;
import com.wolfnine.backend.entity.dto.shopeeShopConfig.PushProductToShopeeShop;
import com.wolfnine.backend.entity.entityEnum.ProductStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    List<ProductDto> findByUserId(long userId);
    ProductDto findById(long id);
    Page<ProductDto> findAllByAuthUser(int limit, int page, String sortBy);
    Product save(Product product);
    List<Product> saveAll(List<Product> products);
    List<Product> findAllByStatus(ProductStatus status);
    Product update(long id, Product product);
    boolean deleteAllByIdIn(List<Long> ids);
    boolean deleteById(long id);
    boolean pushProductToShopeeShop(PushProductToShopeeShop pushProductToShopeeShop);
    boolean pushProductToApi(PushProductToApi pushProductToApi);
}
