package com.wolfnine.backend.entity;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.wolfnine.backend.entity.dto.ProductDto;
import com.wolfnine.backend.entity.entityEnum.ProductStatus;
import lombok.*;

import javax.persistence.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long crawlCategoryId;
    private long categoryId;
    private long userId;
    private String link;
    @Column(columnDefinition = "json")
    private String attributes;
    private ProductStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crawlCategoryId", insertable = false, updatable = false)
    private CrawlCategory crawlCategory;

    public ProductDto toProductDto() {
        return ProductDto.builder()
                .id(id)
                .crawlCategoryId(crawlCategoryId)
                .attributesJson(attributes)
                .categoryId(categoryId)
                .userId(userId)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
