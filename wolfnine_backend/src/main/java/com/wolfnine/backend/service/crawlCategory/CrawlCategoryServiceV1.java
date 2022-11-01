package com.wolfnine.backend.service.crawlCategory;

import com.wolfnine.backend.entity.CrawlCategory;
import com.wolfnine.backend.entity.CrawlConfig;
import com.wolfnine.backend.entity.dto.crawlCategory.CreateCrawlCategoryDto;
import com.wolfnine.backend.entity.entityEnum.CrawlCategoryStatus;
import com.wolfnine.backend.repository.CrawlCategoryRepository;
import com.wolfnine.backend.repository.CrawlConfigRepository;
import com.wolfnine.backend.service.crawlConfig.CrawlConfigService;
import com.wolfnine.backend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CrawlCategoryServiceV1 implements CrawlCategoryService {
    private final CrawlCategoryRepository crawlCategoryRepository;
    private final UserService userService;

    @Override
    public List<CrawlCategory> findByUserId(long userId) {
        return crawlCategoryRepository.findAllByUserId(userId);
    }

    @Override
    public Page<CrawlCategory> findAllByAuthUser(int limit, int page, String sortBy) {
        return crawlCategoryRepository.findAllByUserId(
                userService.findByAuthUser().getId(),
                PageRequest.of(page, limit, Sort.by(sortBy).descending())
        );
    }

    @Override
    public CrawlCategory save(CrawlCategory crawlCategory) {
        return crawlCategoryRepository.save(crawlCategory);
    }

    @Override
    public CrawlCategory save(CreateCrawlCategoryDto crawlCategoryDto) {
        CrawlCategory crawlCategory = crawlCategoryDto.toCrawlCategory();
        crawlCategory.setUserId(userService.findByAuthUser().getId());
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


    @Override
    public CrawlCategory update(long id, CreateCrawlCategoryDto createCrawlCategoryDto) {
        Optional<CrawlCategory> optionalCrawlCategory = crawlCategoryRepository.findById(id);
        if(optionalCrawlCategory.isPresent()) {
            CrawlCategory crawlCategory = optionalCrawlCategory.get();
            crawlCategory.setName(createCrawlCategoryDto.getName());
            crawlCategory.setStatus(createCrawlCategoryDto.getStatus());
            crawlCategory.setLink(createCrawlCategoryDto.getLink());
            crawlCategory.setCategoryId(createCrawlCategoryDto.getCategoryId());
            crawlCategory.setCrawlConfigId(createCrawlCategoryDto.getCrawlConfigId());
            return crawlCategoryRepository.save(crawlCategory);
        }
        return null;
    }

    @Override
    public boolean delete(long id) {
        if(findById(id) != null) {
            crawlCategoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
