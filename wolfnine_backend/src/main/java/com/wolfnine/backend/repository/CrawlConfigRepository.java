package com.wolfnine.backend.repository;

import com.wolfnine.backend.entity.CrawlConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CrawlConfigRepository extends JpaRepository<CrawlConfig, Long> {
    List<CrawlConfig> findAllByUserId(long userId);
}
