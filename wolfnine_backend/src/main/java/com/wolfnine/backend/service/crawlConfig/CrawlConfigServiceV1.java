package com.wolfnine.backend.service.crawlConfig;

import com.google.gson.Gson;
import com.wolfnine.backend.entity.CrawlConfig;
import com.wolfnine.backend.entity.dto.crawlConfig.SaveCrawlConfigDto;
import com.wolfnine.backend.repository.CrawlConfigRepository;
import com.wolfnine.backend.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CrawlConfigServiceV1 implements CrawlConfigService{
    private final CrawlConfigRepository crawlConfigRepository;
    private final AuthUtil authUtil;

    @Override
    public Page<?> findByUserId(long userId, int page, int limit, String sortBy) {
        return crawlConfigRepository.findAllByUserId(userId, PageRequest.of(page, limit, Sort.by(sortBy).descending()));
    }

    @Override
    public Page<?> findByAuthUser(int limit, int page, String sortBy) {
        return crawlConfigRepository.findAllByUserId(
                authUtil.getAuthUser().getId(),
                PageRequest.of(page, limit, Sort.by(sortBy).descending())
        );
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
