package com.wolfnine.backend.service.crawlConfig;

import com.wolfnine.backend.entity.CrawlConfig;
import com.wolfnine.backend.entity.dto.crawlConfig.SaveCrawlConfigDto;
import com.wolfnine.backend.repository.CrawlConfigRepository;
import com.wolfnine.backend.repository.UserRepository;
import com.wolfnine.backend.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CrawlConfigServiceV1 implements CrawlConfigService{
    private final CrawlConfigRepository crawlConfigRepository;
    private final AuthUtil authUtil;

    @Override
    public List<CrawlConfig> findByUserId(long userId) {
        return crawlConfigRepository.findAllByUserId(userId);
    }

    @Override
    public List<CrawlConfig> findByAuthUser() {
        return crawlConfigRepository.findAllByUserId(authUtil.getAuthUser().getId());
    }

    @Override
    public CrawlConfig save(SaveCrawlConfigDto saveCrawlConfigDto) {
        CrawlConfig crawlConfig = saveCrawlConfigDto.toCrawlConfig();
        System.out.println("User Id: " + authUtil.getAuthUser().getId());
        crawlConfig.setUserId(authUtil.getAuthUser().getId());
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

    @Override
    public CrawlConfig update(long id, SaveCrawlConfigDto saveCrawlConfigDto) {
        if(findById(id) != null) {
            CrawlConfig crawlConfig = saveCrawlConfigDto.toCrawlConfig();
            crawlConfig.setId(id);
            crawlConfig.setUserId(authUtil.getAuthUser().getId());
            return crawlConfigRepository.save(crawlConfig);
        }
        return null;
    }

    @Override
    public boolean delete(long id) {
        if(findById(id) != null) {
            crawlConfigRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
