package com.wolfnine.backend.entity;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.wolfnine.backend.entity.base.BaseEntity;
import com.wolfnine.backend.entity.entityEnum.CrawlConfigTemplateStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "crawl_config_templates")
public class CrawlConfigTemplate extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String name;
    @Column(columnDefinition = "json")
    @JsonRawValue
    private String selectors;
    @Column(columnDefinition = "json")
    @JsonRawValue
    private String selectorDetails;
    private long userId;
    private CrawlConfigTemplateStatus status;
}
