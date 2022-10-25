package com.wolfnine.backend.entity.dto.crawlCategory;

import com.wolfnine.backend.entity.CrawlCategory;
import com.wolfnine.backend.entity.CrawlConfig;
import com.wolfnine.backend.entity.Product;
import com.wolfnine.backend.entity.User;
import com.wolfnine.backend.entity.entityEnum.CrawlCategoryStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCrawlCategoryDto {
    private long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String link;
    @NotNull
    private long crawlConfigId;
    private long userId;
    private long categoryId;
    private CrawlCategoryStatus status;

    public CrawlCategory toCrawlCategory() {
        return CrawlCategory.builder()
                .id(id)
                .name(name)
                .link(link)
                .crawlConfigId(crawlConfigId)
                .categoryId(categoryId)
                .status(status)
                .userId(userId)
                .build();
    }
}
