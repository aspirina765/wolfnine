package com.wolfnine.backend.entity.dto.pushProductApiConfig;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PushProductToApi {
    @NotNull
    private List<Long> productIds;
    @NotNull
    private long pushProductApiConfigId;
}
