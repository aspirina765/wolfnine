package com.wolfnine.backend.entity.shopee;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopeeProductImage {
    private List<String> image_id_list;
}
