package com.wolfnine.backend.entity;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.wolfnine.backend.entity.entityEnum.CrawlConfigStatus;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "crawl_configs")
public class CrawlConfig {
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
    @Column(columnDefinition = "json")
    @JsonRawValue
    private String selectors;
    @Column(columnDefinition = "json")
    @JsonRawValue
    private String selectorDetails;
    private long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    @OneToMany(mappedBy = "crawlConfig", fetch = FetchType.LAZY)
    private List<CrawlCategory> crawlCategories;
}
