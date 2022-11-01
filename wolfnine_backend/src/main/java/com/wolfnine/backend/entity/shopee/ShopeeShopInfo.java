package com.wolfnine.backend.entity.shopee;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopeeShopInfo {
    private String shop_name;
    private String region;
    private String status;
}
