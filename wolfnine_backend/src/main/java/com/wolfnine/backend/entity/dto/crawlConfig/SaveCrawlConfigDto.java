package com.wolfnine.backend.entity.dto.crawlConfig;

import com.wolfnine.backend.entity.CrawlConfig;
import com.wolfnine.backend.entity.entityEnum.CrawlConfigStatus;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveCrawlConfigDto {
    @NotEmpty
    private String name;
    @NotEmpty
    private String baseUrl;
    private CrawlConfigStatus status;
    @NotEmpty
    private String selectors;
    @NotEmpty
    private String selectorDetails;
    @NotEmpty
    private String selectorList;

    public CrawlConfig toCrawlConfig() {
        return CrawlConfig.builder()
                .name(name)
                .selectorList(selectorList)
                .selectorDetails(selectorDetails)
                .status(status)
                .selectors(selectors)
                .baseUrl(baseUrl)
                .build();
    }
}
