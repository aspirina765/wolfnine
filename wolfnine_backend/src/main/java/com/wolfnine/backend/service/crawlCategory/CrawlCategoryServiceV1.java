package com.wolfnine.backend.service.crawlCategory;

import com.wolfnine.backend.entity.CrawlCategory;
import com.wolfnine.backend.entity.CrawlConfig;
import com.wolfnine.backend.repository.CrawlCategoryRepository;
import com.wolfnine.backend.repository.CrawlConfigRepository;
import com.wolfnine.backend.service.crawlConfig.CrawlConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CrawlCategoryServiceV1 implements CrawlCategoryService {
    private final CrawlCategoryRepository crawlCategoryRepository;

    @Override
    public List<CrawlCategory> findByUserId(long userId) {
        return crawlCategoryRepository.findAllByUserId(userId);
    }

    @Override
    public CrawlCategory save(CrawlCategory crawlCategory) {
        return crawlCategoryRepository.save(crawlCategory);
    }

    @Override
    public CrawlCategory findById(long id) {
        Optional<CrawlCategory> crawlCategoryOptional = crawlCategoryRepository.findById(id);
        if(crawlCategoryOptional.isPresent()) {
            return crawlCategoryOptional.get();
        }
        return null;
    }
}
