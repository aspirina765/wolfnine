package com.wolfnine.backend.repository;

import com.wolfnine.backend.entity.CrawlConfigTemplate;
import com.wolfnine.backend.entity.entityEnum.CrawlConfigTemplateStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrawlConfigTemplateRepository extends JpaRepository<CrawlConfigTemplate, Long> {
    Page<CrawlConfigTemplate> findAll(Pageable pageable);
    List<CrawlConfigTemplate> findAllByUserIdOrStatus(long user, CrawlConfigTemplateStatus status);
}
