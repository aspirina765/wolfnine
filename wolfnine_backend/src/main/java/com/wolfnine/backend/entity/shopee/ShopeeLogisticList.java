package com.wolfnine.backend.entity.shopee;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopeeLogisticList {
    private List<ShopeeLogisticChannel> logistics_channel_list;
}
