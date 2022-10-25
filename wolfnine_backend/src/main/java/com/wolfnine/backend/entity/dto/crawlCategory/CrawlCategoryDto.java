package com.wolfnine.backend.entity.dto.crawlCategory;

import com.wolfnine.backend.entity.CrawlConfig;
import com.wolfnine.backend.entity.Product;
import com.wolfnine.backend.entity.User;
import com.wolfnine.backend.entity.entityEnum.CrawlCategoryStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrawlCategoryDto {
    private long id;
    private String name;
    private String link;
    private long crawlConfigId;
    private long userId;
    private long categoryId;
    private CrawlCategoryStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private User user;
    private CrawlConfig crawlConfig;
}
