package com.wolfnine.backend.service.product;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wolfnine.backend.constant.CrawlConstant;
import com.wolfnine.backend.entity.Product;
import com.wolfnine.backend.entity.PushProductApiConfig;
import com.wolfnine.backend.entity.ShopeeShopConfig;
import com.wolfnine.backend.entity.dto.ProductDto;
import com.wolfnine.backend.entity.dto.pushProductApiConfig.PushProductToApi;
import com.wolfnine.backend.entity.dto.shopeeShopConfig.PushProductToShopeeShop;
import com.wolfnine.backend.entity.entityEnum.ProductStatus;
import com.wolfnine.backend.entity.shopee.*;
import com.wolfnine.backend.repository.ProductRepository;
import com.wolfnine.backend.repository.PushProductApiConfigRepository;
import com.wolfnine.backend.repository.ShopeeShopConfigRepository;
import com.wolfnine.backend.service.shopeeShopConfig.ShopeeShopConfigService;
import com.wolfnine.backend.service.user.UserService;
import com.wolfnine.backend.util.FileUtil;
import com.wolfnine.backend.util.JsonUtil;
import com.wolfnine.backend.util.NumberUtil;
import com.wolfnine.backend.util.ShopeeUtil;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceV1 implements ProductService {
    final ProductRepository productRepository;
    final UserService userService;
    final ShopeeShopConfigService shopeeShopConfigService;
    final Environment environment;
    final ShopeeShopConfigRepository shopeeShopConfigRepository;
    final PushProductApiConfigRepository pushProductApiConfigRepository;
    private static final Dotenv env = Dotenv.configure().load();
    private static String partnerKey = env.get("SHOPEE_PARTNER_KEY");
    private static long partnerId = Long.parseLong(env.get("SHOPEE_PARTNER_ID"));

    @Override
    public List<ProductDto> findByUserId(long userId) {
        List<Product> products = productRepository.findAllByUserId(userId);
        List<ProductDto> productDtos = products.stream()
                .map(product -> product.toProductDto())
                .collect(Collectors.toList());
        return productDtos;
    }

    @Override
    public ProductDto findById(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            return product.toProductDto();
        }
        return null;
    }

    @Override
    public Page<ProductDto> findAllByAuthUser(int limit, int page, String sortBy) {
        Page<Product> productPage = productRepository.findAllByUserId(
                userService.findByAuthUser().getId(),
                PageRequest.of(page, limit, Sort.by(sortBy).descending())
        );
        List<ProductDto> products = productPage.stream()
                .map(product -> product.toProductDto())
                .collect(Collectors.toList());
        return new PageImpl<>(products, PageRequest.of(page, limit, Sort.by(sortBy).descending()), productPage.getTotalElements());
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> saveAll(List<Product> products) {
        return productRepository.saveAll(products);
    }

    @Override
    public List<Product> findAllByStatus(ProductStatus status) {
        return productRepository.findAllByStatus(status);
    }

    @Override
    public Product update(long id, Product product) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            return productRepository.save(product);
        }
        return null;
    }

    @Override
    @Transactional
    public boolean deleteAllByIdIn(List<Long> ids) {
        try {
            productRepository.deleteByIdIn(ids);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteById(long id) {
        try {
            productRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean pushProductToShopeeShop(PushProductToShopeeShop pushProductToShopeeShop) {
        ShopeeShopConfig shopeeShopConfig = shopeeShopConfigService.findById(pushProductToShopeeShop.getShopeeShopConfigId());
        if(shopeeShopConfig != null) {
            try {
                for (Long productId : pushProductToShopeeShop.getProductIds()
                     ) {
                    Optional<Product> optionalProduct = productRepository.findById(productId);
                    if(optionalProduct.isPresent()) {
                        Product product = optionalProduct.get();
                        System.out.println("Product ID"  + product.getId());
                        RestTemplate restTemplate = new RestTemplate();
                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        String apiUrl = ShopeeUtil.addProductItem(shopeeShopConfig.getAccessToken(), partnerId, shopeeShopConfig.getShopId(), partnerKey);

                        JsonObject jsonObject = new JsonObject();
                        JsonParser jsonParser = new JsonParser();
                        JsonArray jsonArray = jsonParser.parse(product.getAttributes()).getAsJsonArray();
//
                        JsonObject itemName = JsonUtil.getItemFromArrayByKey("title", jsonArray);
                        JsonObject itemPrice = JsonUtil.getItemFromArrayByKey("original_price", jsonArray);
                        JsonObject itemDesc = JsonUtil.getItemFromArrayByKey("description", jsonArray);
                        List<ShopeeLogisticInfo> logisticInfos = new ArrayList<>();
                        ShopeeLogisticInfo logisticInfoItem = ShopeeLogisticInfo.builder()
                                .logistic_id(pushProductToShopeeShop.getLogisticId())
                                .enabled(true)
                                .build();
                        logisticInfos.add(logisticInfoItem);
                        List<String> imageIds = new ArrayList<>();
                        JsonObject thumbnail = JsonUtil.getItemFromArrayByKey("thumbnail", jsonArray).getAsJsonObject();
                        String imagePath = FileUtil.saveImageWithUrl(thumbnail.get(CrawlConstant.CONFIG_KEY_ITEM_VALUE).getAsString());
                        imageIds.add(ShopeeUtil.uploadImage(partnerId, partnerKey, imagePath));
                        ShopeeProductImage shopeeProductImage = ShopeeProductImage.builder()
                                .image_id_list(imageIds)
                                .build();
                        List<ShopeeProductSellerStock> shopeeProductSellerStockList = new ArrayList<>();
                        ShopeeProductSellerStock productSellerStock = ShopeeProductSellerStock.builder()
                                .stock(pushProductToShopeeShop.getSellerStock())
                                .build();
                        shopeeProductSellerStockList.add(productSellerStock);
                        ShopeeProductBrand shopeeProductBrand = ShopeeProductBrand.builder()
                                .brand_id(pushProductToShopeeShop.getBrandId())
                                .build();
                        ShopeeProduct shopeeProduct = ShopeeProduct.builder()
                                .item_name(itemName.get(CrawlConstant.CONFIG_KEY_ITEM_VALUE).getAsString())
                                .description(itemDesc.get(CrawlConstant.CONFIG_KEY_ITEM_VALUE).getAsString())
                                .original_price(NumberUtil.removeSymbolCurrency(itemPrice.get(CrawlConstant.CONFIG_KEY_ITEM_VALUE).getAsString()))
                                .item_status("UNLIST")
                                .logistic_info(logisticInfos)
                                .category_id(pushProductToShopeeShop.getCategoryId())
                                .image(shopeeProductImage)
                                .seller_stock(shopeeProductSellerStockList)
                                .weight(1)
                                .brand(shopeeProductBrand)
                                .build();
                        HttpEntity<ShopeeProduct> httpEntity = new HttpEntity<>(shopeeProduct, headers);
                        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, httpEntity, String.class);
                        System.out.println("Push Product Response: " + response.toString());
                    }
                }
                return true;
            }catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean pushProductToApi(PushProductToApi pushProductToApi) {
        Optional<PushProductApiConfig> pushProductApiConfig = pushProductApiConfigRepository.findById(pushProductToApi.getPushProductApiConfigId());
        if(pushProductApiConfig.isPresent()) {
            PushProductApiConfig pushProductApiConfig1 = pushProductApiConfig.get();
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            for(Long productId : pushProductToApi.getProductIds()) {
                Optional<Product> optionalProduct = productRepository.findById(productId);
                if(optionalProduct.isPresent()) {
                    Product product= optionalProduct.get();
                    JsonParser jsonParser = new JsonParser();
                    JsonArray productAttributes = jsonParser.parse(product.getAttributes()).getAsJsonArray();
                    JsonObject jsonObject = new JsonObject();
                    for (JsonElement jsonElement : productAttributes) {
                        JsonObject jsonElm = jsonElement.getAsJsonObject();
                        jsonObject.add(jsonElm.get(CrawlConstant.CONFIG_SELECTOR_KEY).getAsString(), jsonElm.get(CrawlConstant.CONFIG_KEY_ITEM_VALUE));
                    }
                    HttpEntity<String> request = new HttpEntity<String>(jsonObject.toString(), headers);
                    String result = restTemplate.postForObject(pushProductApiConfig1.getApiUrl(), request, String.class);
                    System.out.println(result);
                }
            }
            return true;
        }
        return false;
    }
}
