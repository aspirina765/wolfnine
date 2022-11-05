package com.wolfnine.backend.service.crawlConfigTemplate;

import com.wolfnine.backend.entity.CrawlConfigTemplate;
import com.wolfnine.backend.entity.dto.crawlConfigTemplate.SaveCrawlConfigTemplate;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CrawlConfigTemplateService {
     List<CrawlConfigTemplate> findAllByAuthUser();
     Page<CrawlConfigTemplate> findAll(int page, int limit, String sortBy);
     CrawlConfigTemplate findById(long id);
     CrawlConfigTemplate save(SaveCrawlConfigTemplate saveCrawlConfigTemplate);
     CrawlConfigTemplate update(long id, SaveCrawlConfigTemplate saveCrawlConfigTemplate);
     boolean deleteById(long id);
}
