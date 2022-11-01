package com.wolfnine.backend.repository;

import com.wolfnine.backend.entity.CrawlCategory;
import com.wolfnine.backend.entity.entityEnum.CrawlCategoryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrawlCategoryRepository extends JpaRepository<CrawlCategory, Long> {
    List<CrawlCategory> findAllByUserId(long userId);
    List<CrawlCategory> findAllByStatus(CrawlCategoryStatus status);
    Page<CrawlCategory> findAllByUserId(long userId, Pageable pageable);
}
