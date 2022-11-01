package com.wolfnine.backend.repository;

import com.wolfnine.backend.entity.CrawlConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CrawlConfigRepository extends JpaRepository<CrawlConfig, Long> {
    Page<?> findAllByUserId(long userId, Pageable pageable);
}
