package com.wolfnine.backend.repository;

import com.wolfnine.backend.entity.PushProductApiConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PushProductApiConfigRepository extends JpaRepository<PushProductApiConfig, Long> {
    Page<PushProductApiConfig> findAllByUserId(long userId, Pageable pageable);
}
