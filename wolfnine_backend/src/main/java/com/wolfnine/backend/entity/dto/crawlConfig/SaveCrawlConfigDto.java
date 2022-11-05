package com.wolfnine.backend.entity.dto.crawlConfig;

import com.google.gson.Gson;
import com.wolfnine.backend.entity.CrawlConfig;
import com.wolfnine.backend.entity.Selector;
import com.wolfnine.backend.entity.entityEnum.CrawlConfigStatus;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

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
    private List<Selector> selectors;
    @NotEmpty
    private List<Selector> selectorDetails;
    @NotEmpty
    private String selectorList;
    private long templateId;

    public CrawlConfig toCrawlConfig() {
        Gson gson = new Gson();
        return CrawlConfig.builder()
                .name(name)
                .selectorList(selectorList)
                .selectorDetails(gson.toJson(selectorDetails))
                .status(CrawlConfigStatus.ACTIVE)
                .selectors(gson.toJson(selectors))
                .templateId(templateId)
                .baseUrl(baseUrl)
                .build();
    }
}
