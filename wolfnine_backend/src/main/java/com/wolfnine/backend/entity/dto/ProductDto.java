package com.wolfnine.backend.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import lombok.*;
import javax.persistence.Column;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private long id;
    private long crawlCategoryId;
    private long categoryId;
    private long userId;
    @JsonIgnore
    private String attributesJson;
    @Getter(AccessLevel.NONE)
    private Object attributes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Object getAttributes() {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>() {}.getType();
        return gson.fromJson(attributesJson, type);
    }
}
