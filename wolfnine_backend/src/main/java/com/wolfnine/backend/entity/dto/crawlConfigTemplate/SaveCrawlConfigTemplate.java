package com.wolfnine.backend.entity.dto.crawlConfigTemplate;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.google.gson.Gson;
import com.wolfnine.backend.entity.CrawlConfigTemplate;
import com.wolfnine.backend.entity.Selector;
import com.wolfnine.backend.entity.entityEnum.CrawlConfigTemplateStatus;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveCrawlConfigTemplate {
    private long id;
    @NotNull
    private String name;
    private List<Selector> selectors;
    private List<Selector> selectorDetails;
    private long userId;
    @NotNull
    private CrawlConfigTemplateStatus status;

    public CrawlConfigTemplate toCrawlConfigTemplate() {
        Gson gson = new Gson();
        return CrawlConfigTemplate.builder()
                .name(name)
                .selectors(gson.toJson(selectors))
                .selectorDetails(gson.toJson(selectorDetails))
                .status(status)
                .userId(userId)
                .build();
    }
}
