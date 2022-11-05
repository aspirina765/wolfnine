package com.wolfnine.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.wolfnine.backend.entity.base.BaseEntity;
import com.wolfnine.backend.entity.entityEnum.CrawlConfigStatus;
import lombok.*;
import org.hibernate.annotations.Type;
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
@Table(name = "crawl_configs")
public class CrawlConfig extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String baseUrl;
    private String selectorList;
    private String selectorLink;
    @Enumerated
    private CrawlConfigStatus status;
    @Column(columnDefinition = "json")
    @JsonRawValue
    private String selectors;
    @Column(columnDefinition = "json")
    @JsonRawValue
    private String selectorDetails;
    private long userId;
    private long templateId;
    @JsonBackReference
    @OneToMany(mappedBy = "crawlConfig", fetch = FetchType.LAZY)
    private List<CrawlCategory> crawlCategories;
}
