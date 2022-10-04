package com.wolfnine.backend.service.crawlConfig;

import com.wolfnine.backend.entity.CrawlConfig;
import com.wolfnine.backend.repository.CrawlConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CrawlConfigServiceV1 implements CrawlConfigService{
    private final CrawlConfigRepository crawlConfigRepository;

    @Override
    public List<CrawlConfig> findByUserId(long userId) {
        return crawlConfigRepository.findAllByUserId(userId);
    }

    @Override
    public CrawlConfig save(CrawlConfig crawlConfig) {
        return crawlConfigRepository.save(crawlConfig);
    }

    @Override
    public CrawlConfig findById(long id) {
        Optional<CrawlConfig> crawlConfig = crawlConfigRepository.findById(id);
        if(crawlConfig.isPresent()) {
            return crawlConfig.get();
        }
        return null;
    }
}
