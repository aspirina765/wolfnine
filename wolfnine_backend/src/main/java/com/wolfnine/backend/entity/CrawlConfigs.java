package com.wolfnine.backend.entity;

import com.wolfnine.backend.entity.entityEnum.CrawlConfigStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "crawl_configs")
public class CrawlConfigs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String baseUrl;
    private String selectorList;
    private String selectorThumbnail;
    private String selectorTitle;
    private String selectorPrice;
    private String selectorDiscountPrice;
    private String selectorLink;
    private CrawlConfigStatus status;
    @Column(columnDefinition = "JSON")
    private String selectors;
    @Column(columnDefinition = "JSON")
    private String selectorDetails;
    private long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

}
