package com.wolfnine.backend.entity;

import com.wolfnine.backend.entity.entityEnum.CrawlCategoryStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "crawl_categories")
public class CrawlCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String link;
    private long crawlConfigId;
    private long userId;
    private CrawlCategoryStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
