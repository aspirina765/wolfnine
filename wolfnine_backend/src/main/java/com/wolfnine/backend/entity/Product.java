package com.wolfnine.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.wolfnine.backend.entity.dto.ProductDto;
import com.wolfnine.backend.entity.entityEnum.ProductStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
    @JsonRawValue
    private String attributes;
    private ProductStatus status;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "crawlCategoryId", insertable = false, updatable = false)
    private CrawlCategory crawlCategory;

    public ProductDto toProductDto() {
        return ProductDto.builder()
                .id(id)
                .crawlCategoryId(crawlCategoryId)
                .link(link)
                .attributes(attributes)
                .categoryId(categoryId)
                .crawlCategory(crawlCategory)
                .userId(userId)
                .status(status)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
