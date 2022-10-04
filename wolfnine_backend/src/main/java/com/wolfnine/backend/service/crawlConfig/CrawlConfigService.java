package com.wolfnine.backend.service.crawlConfig;

import com.wolfnine.backend.entity.CrawlConfig;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CrawlConfigService {
    List<CrawlConfig> findByUserId(long userId);
    CrawlConfig save(CrawlConfig crawlConfig);
    CrawlConfig findById(long id);
}
