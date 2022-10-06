package com.wolfnine.backend.service.crawlCategory;

import com.wolfnine.backend.entity.CrawlCategory;

import java.util.List;

public interface CrawlCategoryService {
    List<CrawlCategory> findByUserId(long userId);
    CrawlCategory save(CrawlCategory crawlCategory);
    CrawlCategory findById(long id);
    List<CrawlCategory> findAll();
}
