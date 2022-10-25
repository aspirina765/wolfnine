package com.wolfnine.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.wolfnine.backend.entity.base.BaseEntity;
import com.wolfnine.backend.entity.entityEnum.CrawlCategoryStatus;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "crawl_categories")
public class CrawlCategory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String link;
    private long crawlConfigId;
    private long userId;
    private long categoryId;
    private CrawlCategoryStatus status;
    @OneToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    @JsonManagedReference
    private User user;
    @ManyToOne
    @JoinColumn(name = "crawlConfigId", insertable = false, updatable = false)
    @JsonManagedReference
    private CrawlConfig crawlConfig;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "crawlCategory")
    private List<Product> products;
}
