package com.wolfnine.backend.service.shopeeShopConfig;

import com.wolfnine.backend.entity.ShopeeShopConfig;
import com.wolfnine.backend.entity.dto.shopeeShopConfig.SaveShopeeShopConfig;
import com.wolfnine.backend.entity.shopee.*;
import com.wolfnine.backend.entity.shopee.brand.ShopeeBrand;
import com.wolfnine.backend.entity.shopee.brand.ShopeeBrandResponse;
import com.wolfnine.backend.repository.ShopeeShopConfigRepository;
import com.wolfnine.backend.service.user.UserService;
import com.wolfnine.backend.util.ShopeeUtil;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopeeShopConfigServiceV1 implements ShopeeShopConfigService{
    private final ShopeeShopConfigRepository shopeeShopConfigRepository;
    private final UserService userService;
    private static final Dotenv env = Dotenv.configure().load();
    private static String partnerKey = env.get("SHOPEE_PARTNER_KEY");
    private static long partnerId = Long.parseLong(env.get("SHOPEE_PARTNER_ID"));

    @Override
    public List<ShopeeShopConfig> findAll() {
        return shopeeShopConfigRepository.findAll();
    }

    @Override
    public List<ShopeeShopConfig> findAllByAuthUser() {
        return shopeeShopConfigRepository.findAllByUserId(userService.findByAuthUser().getId());
    }

    @Override
    public Page<ShopeeShopConfig> findAllByAuthUser(int limit, int page, String sortBy) {
        return shopeeShopConfigRepository.findAllByUserId(
                userService.findByAuthUser().getId(),
                PageRequest.of(page, limit, Sort.by(sortBy).descending())
        );
    }

    @Override
    public ShopeeShopConfig save(SaveShopeeShopConfig saveShopeeShopConfig) throws ParseException, IOException {
        saveShopeeShopConfig.setUserId(userService.findByAuthUser().getId());
        ShopeeCredential shopeeCredential = ShopeeUtil.getTokenShopLevel(saveShopeeShopConfig.getCode(), partnerId, partnerKey, saveShopeeShopConfig.getShopId());
        ShopeeShopInfo shopeeShopInfo = ShopeeUtil.getShopInfo(
          shopeeCredential.getAccess_token(),
          partnerId,
          saveShopeeShopConfig.getShopId(),
          partnerKey
        );
        saveShopeeShopConfig.setAccessToken(shopeeCredential.getAccess_token());
        saveShopeeShopConfig.setRefreshToken(shopeeCredential.getRefresh_token());
        ShopeeShopConfig shopeeShopConfig = findByShopId(saveShopeeShopConfig.getShopId());
        LocalDateTime expiredAt = LocalDateTime.now().plusSeconds(shopeeCredential.getExpire_in());
        saveShopeeShopConfig.setExpiredAt(expiredAt);
        System.out.println(shopeeShopInfo.getShop_name());
        saveShopeeShopConfig.setName(shopeeShopInfo.getShop_name());
        if(shopeeShopConfig != null) {
            shopeeShopConfig.setAccessToken(shopeeCredential.getAccess_token());
            shopeeShopConfig.setRefreshToken(shopeeCredential.getRefresh_token());
            shopeeShopConfig.setExpiredAt(expiredAt);
            return shopeeShopConfigRepository.save(shopeeShopConfig);
        }
        return shopeeShopConfigRepository.save(saveShopeeShopConfig.toShopeeShopConfig());
    }

    @Override
    public ShopeeShopConfig findById(long id) {
        Optional<ShopeeShopConfig> optionalShopeeShopConfig = shopeeShopConfigRepository.findById(id);
        if(optionalShopeeShopConfig.isPresent()) {
            return optionalShopeeShopConfig.get();
        }
        return null;
    }

    @Override
    public List<ShopeeCategory> getShopeeCategoryList(long shopeeShopConfigId, int page, int limit) {
        ShopeeShopConfig shopeeShopConfig = findById(shopeeShopConfigId);
        if(shopeeShopConfig != null) {
            refreshAccessTokenShopeeShop(shopeeShopConfig);
            ShopeeCategoryResponse shopeeCategoryResponse = ShopeeUtil.getProductCategoryList(
                    shopeeShopConfig.getAccessToken(),
                    partnerId,
                    shopeeShopConfig.getShopId(),
                    partnerKey,
                    limit,
                    page
            );
            return shopeeCategoryResponse.getResponse().getCategory_list()
                    .stream()
                    .filter(item -> !item.isHas_children())
                    .limit(limit)
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<ShopeeLogisticChannel> getShopeeLogisticChannelList(long shopeeShopConfigId) {
        ShopeeShopConfig shopeeShopConfig = findById(shopeeShopConfigId);
        if(shopeeShopConfig != null) {
            shopeeShopConfig = refreshAccessTokenShopeeShop(shopeeShopConfig);
            ShopeeLogisticChannelResponse logisticChannelResponse = ShopeeUtil.getLogisticChannelList(
                    shopeeShopConfig.getAccessToken(),
                    partnerId,
                    shopeeShopConfig.getShopId(),
                    partnerKey
            );
            return logisticChannelResponse.getResponse().getLogistics_channel_list();
        }
        return null;
    }

    @Override
    public List<ShopeeBrand> getShopeeBrandList(long shopeeShopConfigId, int shopeeCategoryId, int pageSize, int offset) {
        ShopeeShopConfig shopeeShopConfig = findById(shopeeShopConfigId);
        if(shopeeShopConfig != null) {
            try {
                shopeeShopConfig = refreshAccessTokenShopeeShop(shopeeShopConfig);
                ShopeeBrandResponse logisticChannelResponse = ShopeeUtil.getBrandList(
                        shopeeShopConfig.getAccessToken(),
                        partnerId,
                        shopeeShopConfig.getShopId(),
                        partnerKey,
                        shopeeCategoryId,
                        pageSize,
                        offset
                );
                return logisticChannelResponse.getResponse().getBrand_list();
            }catch (Exception e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
        }
        return new ArrayList<>();
    }

    @Override
    public ShopeeShopConfig refreshAccessTokenShopeeShop(ShopeeShopConfig shopeeShopConfig) {
        try {
            LocalDateTime now = LocalDateTime.now();
            System.out.println("Check expired access token: " + now.isAfter(shopeeShopConfig.getExpiredAt()));
            if(now.isAfter(shopeeShopConfig.getExpiredAt())) {
                ShopeeCredential credential = ShopeeUtil.getRefreshTokenShopLevel(
                        shopeeShopConfig.getRefreshToken(),
                        partnerId,
                        partnerKey,
                        shopeeShopConfig.getShopId()
                );
                shopeeShopConfig.setAccessToken(credential.getAccess_token());
                shopeeShopConfig.setRefreshToken(credential.getRefresh_token());
                LocalDateTime expiredAt = LocalDateTime.now().plusSeconds(credential.getExpire_in());
                shopeeShopConfig.setExpiredAt(expiredAt);
                shopeeShopConfigRepository.save(shopeeShopConfig);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return shopeeShopConfig;
    }

    public ShopeeShopConfig findByShopId(long id) {
        Optional<ShopeeShopConfig> optionalShopeeShopConfig = shopeeShopConfigRepository.findByShopId(id);
        if(optionalShopeeShopConfig.isPresent()) {
            return optionalShopeeShopConfig.get();
        }
        return null;
    }
}
