package com.wolfnine.backend.service.crawlCategory;

import com.wolfnine.backend.entity.CrawlCategory;
import com.wolfnine.backend.entity.dto.crawlCategory.CreateCrawlCategoryDto;
import com.wolfnine.backend.entity.entityEnum.CrawlCategoryStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CrawlCategoryService {
    List<CrawlCategory> findByUserId(long userId);
    Page<CrawlCategory> findAllByAuthUser(int limit, int page, String sortBy);
    CrawlCategory save(CrawlCategory crawlCategory);
    CrawlCategory save(CreateCrawlCategoryDto crawlCategoryDto);
    CrawlCategory findById(long id);
    List<CrawlCategory> findAll();
    List<CrawlCategory> findAllByStatus(CrawlCategoryStatus status);
    CrawlCategory update(long id, CrawlCategory crawlCategory);
    CrawlCategory update(long id, CreateCrawlCategoryDto createCrawlCategoryDto);
    boolean delete(long id);
}
