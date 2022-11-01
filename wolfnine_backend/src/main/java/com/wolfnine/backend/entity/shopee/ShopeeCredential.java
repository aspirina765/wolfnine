package com.wolfnine.backend.entity.shopee;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopeeCredential {
    private String access_token;
    private String refresh_token;
    private long expire_in;
}
