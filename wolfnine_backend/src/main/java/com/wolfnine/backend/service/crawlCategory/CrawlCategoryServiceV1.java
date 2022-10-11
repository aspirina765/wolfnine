package com.wolfnine.backend.service.crawlCategory;

import com.wolfnine.backend.entity.CrawlCategory;
import com.wolfnine.backend.entity.CrawlConfig;
import com.wolfnine.backend.entity.entityEnum.CrawlCategoryStatus;
import com.wolfnine.backend.repository.CrawlCategoryRepository;
import com.wolfnine.backend.repository.CrawlConfigRepository;
import com.wolfnine.backend.service.crawlConfig.CrawlConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
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

    @Override
    public List<CrawlCategory> findAll() {
        return crawlCategoryRepository.findAll();
    }

    @Override
    public List<CrawlCategory> findAllByStatus(CrawlCategoryStatus status) {
        return crawlCategoryRepository.findAllByStatus(status);
    }

    @Override
    public CrawlCategory update(long id, CrawlCategory crawlCategory) {
        Optional<CrawlCategory> optionalCrawlCategory = crawlCategoryRepository.findById(id);
        if(optionalCrawlCategory.isPresent()) {
            return crawlCategoryRepository.save(crawlCategory);
        }
        return null;
    }
}
