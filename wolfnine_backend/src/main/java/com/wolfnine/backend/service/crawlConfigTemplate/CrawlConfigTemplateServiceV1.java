package com.wolfnine.backend.service.crawlConfigTemplate;

import com.google.gson.Gson;
import com.wolfnine.backend.entity.CrawlConfigTemplate;
import com.wolfnine.backend.entity.dto.crawlConfigTemplate.SaveCrawlConfigTemplate;
import com.wolfnine.backend.entity.entityEnum.CrawlConfigTemplateStatus;
import com.wolfnine.backend.repository.CrawlConfigTemplateRepository;
import com.wolfnine.backend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CrawlConfigTemplateServiceV1 implements CrawlConfigTemplateService{
    private final CrawlConfigTemplateRepository crawlConfigTemplateRepository;
    private final UserService userService;

    @Override
    public List<CrawlConfigTemplate> findAllByAuthUser() {
        return crawlConfigTemplateRepository.findAllByUserIdOrStatus(userService.findByAuthUser().getId(), CrawlConfigTemplateStatus.PUBLIC);
    }

    @Override
    public Page<CrawlConfigTemplate> findAll(int page, int limit, String sortBy) {
        return crawlConfigTemplateRepository.findAll(PageRequest.of(page, limit, Sort.by(sortBy).descending()));
    }

    @Override
    public CrawlConfigTemplate findById(long id) {
        Optional<CrawlConfigTemplate> optionalCrawlConfigTemplate = crawlConfigTemplateRepository.findById(id);
        if(optionalCrawlConfigTemplate.isPresent()) {
            return optionalCrawlConfigTemplate.get();
        }
        return null;
    }

    @Override
    public CrawlConfigTemplate save(SaveCrawlConfigTemplate saveCrawlConfigTemplate) {
        return crawlConfigTemplateRepository.save(saveCrawlConfigTemplate.toCrawlConfigTemplate());
    }

    @Override
    public CrawlConfigTemplate update(long id, SaveCrawlConfigTemplate saveCrawlConfigTemplate) {
        CrawlConfigTemplate crawlConfigTemplate = findById(id);
        if(crawlConfigTemplate != null) {
            Gson gson = new Gson();
            crawlConfigTemplate.setName(saveCrawlConfigTemplate.getName());
            crawlConfigTemplate.setStatus(saveCrawlConfigTemplate.getStatus());
            crawlConfigTemplate.setSelectors(gson.toJson(saveCrawlConfigTemplate.getSelectors()));
            crawlConfigTemplate.setSelectorDetails(gson.toJson(saveCrawlConfigTemplate.getSelectorDetails()));
            crawlConfigTemplate.setUserId(saveCrawlConfigTemplate.getUserId());
            return crawlConfigTemplateRepository.save(crawlConfigTemplate);
        }
        return null;
    }

    @Override
    public boolean deleteById(long id) {
        if(findById(id) != null) {
            crawlConfigTemplateRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
