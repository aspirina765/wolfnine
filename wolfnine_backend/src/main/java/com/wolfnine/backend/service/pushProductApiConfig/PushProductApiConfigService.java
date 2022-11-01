package com.wolfnine.backend.service.pushProductApiConfig;

import com.wolfnine.backend.entity.PushProductApiConfig;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface PushProductApiConfigService {
    Page<PushProductApiConfig> findAllByAuthUser(int page, int limit, String sortBy);
    PushProductApiConfig findById(long id);
    PushProductApiConfig update(long id, PushProductApiConfig pushProductApiConfig);
    PushProductApiConfig save(PushProductApiConfig pushProductApiConfig);
    boolean deleteById(long id);
}
