package com.wolfnine.backend.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "shopee_shop_configs")
public class ShopeeShopConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private long shopId;
    private String accessToken;
    private String refreshToken;
    private LocalDateTime expiredAt;
    private long userId;
}
