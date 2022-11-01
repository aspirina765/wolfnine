package com.wolfnine.backend.entity.shopee;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopeeProduct {
    private String description;
    private String item_name;
    private long category_id;
    private List<ShopeeLogisticInfo> logistic_info;
    private String item_status;
    private double original_price;
    private ShopeeProductImage image;
    private List<ShopeeProductSellerStock> seller_stock;
    private float weight;
    private ShopeeProductBrand brand;
}
