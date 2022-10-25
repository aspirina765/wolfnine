package com.wolfnine.backend.service.crawlConfig;

import com.wolfnine.backend.entity.CrawlConfig;
import com.wolfnine.backend.entity.dto.crawlConfig.SaveCrawlConfigDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface CrawlConfigService {
    Page<?> findByUserId(long userId, int page, int limit, String sortBy);
    Page<?> findByAuthUser(int limit, int page, String sortBy);
    CrawlConfig save(SaveCrawlConfigDto saveCrawlConfigDto);
    CrawlConfig findById(long id);
    CrawlConfig update(long id, SaveCrawlConfigDto saveCrawlConfigDto);
    boolean delete(long id);
}
