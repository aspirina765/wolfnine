package com.wolfnine.backend.repository;

import com.wolfnine.backend.entity.CrawlCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CrawlCategoryRepository extends JpaRepository<CrawlCategory, Long> {
    List<CrawlCategory> findAllByUserId(long userId);
}
