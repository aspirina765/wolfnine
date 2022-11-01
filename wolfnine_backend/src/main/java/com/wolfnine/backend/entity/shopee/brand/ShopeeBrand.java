package com.wolfnine.backend.entity.shopee.brand;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopeeBrand {
    private String original_brand_name;
    private int brand_id;
    private String display_brand_name;
}
