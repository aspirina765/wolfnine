package com.wolfnine.backend.entity.dto.shopeeShopConfig;

import com.wolfnine.backend.entity.ShopeeShopConfig;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveShopeeShopConfig {
    private long id;
    private String name;
    @NotNull
    private String code;
    @NotNull
    private long shopId;
    private String accessToken;
    private String refreshToken;
    private LocalDateTime expiredAt;
    private long userId;

    public ShopeeShopConfig toShopeeShopConfig() {
        return ShopeeShopConfig.builder()
                .name(name)
                .shopId(shopId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(userId)
                .expiredAt(expiredAt)
                .build();
    }
}
