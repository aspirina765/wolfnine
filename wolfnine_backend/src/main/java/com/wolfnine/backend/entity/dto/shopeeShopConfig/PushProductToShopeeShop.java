package com.wolfnine.backend.entity.dto.shopeeShopConfig;

import lombok.*;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.checkerframework.common.value.qual.MinLen;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PushProductToShopeeShop {
    @NotNull
    private long shopeeShopConfigId;
    @NotNull
    @MinLen(value = 1)
    private List<Long> productIds;
    @NotNull
    private long logisticId;
    @NotNull
    private long categoryId;
    @NotNull
    private long brandId;
    @NotNull
    private int sellerStock;
}
