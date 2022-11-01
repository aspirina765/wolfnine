package com.wolfnine.backend.entity.shopee;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopeeLogisticInfo {
    private long logistic_id;
    private boolean enabled;
}
