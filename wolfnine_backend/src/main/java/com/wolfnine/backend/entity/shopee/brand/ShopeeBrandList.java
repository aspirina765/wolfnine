package com.wolfnine.backend.entity.shopee.brand;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopeeBrandList {
    private List<ShopeeBrand> brand_list;
}
