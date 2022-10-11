package com.wolfnine.backend.service.crawlConfig;

import com.wolfnine.backend.entity.CrawlConfig;
import com.wolfnine.backend.entity.dto.crawlConfig.SaveCrawlConfigDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CrawlConfigService {
    List<CrawlConfig> findByUserId(long userId);
    List<CrawlConfig> findByAuthUser();
    CrawlConfig save(SaveCrawlConfigDto saveCrawlConfigDto);
    CrawlConfig findById(long id);
    CrawlConfig update(long id, SaveCrawlConfigDto saveCrawlConfigDto);
    boolean delete(long id);
}
