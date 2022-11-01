package com.wolfnine.backend.entity.shopee;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopeeCategory {
    private long category_id;
    private String display_category_name;
    private String original_category_name;
    private int parent_category_id;
    private boolean has_children;
}
