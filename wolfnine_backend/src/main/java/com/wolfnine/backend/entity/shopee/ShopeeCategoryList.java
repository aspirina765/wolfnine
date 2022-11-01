package com.wolfnine.backend.entity.shopee;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopeeCategoryList {
    private List<ShopeeCategory> category_list;
}
