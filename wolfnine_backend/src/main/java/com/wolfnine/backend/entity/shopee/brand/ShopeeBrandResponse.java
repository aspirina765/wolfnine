package com.wolfnine.backend.entity.shopee.brand;

import com.wolfnine.backend.entity.shopee.ShopeeCategoryList;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopeeBrandResponse {
    private ShopeeBrandList response;
}
