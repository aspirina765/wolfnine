package com.wolfnine.backend.service.pushProductApiConfig;

import com.wolfnine.backend.entity.PushProductApiConfig;
import com.wolfnine.backend.repository.PushProductApiConfigRepository;
import com.wolfnine.backend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PushProductApiConfigServiceV1 implements PushProductApiConfigService{
    private final UserService userService;
    private final PushProductApiConfigRepository pushProductApiConfigRepository;

    @Override
    public Page<PushProductApiConfig> findAllByAuthUser(int page, int limit, String sortBy) {
        return pushProductApiConfigRepository.findAllByUserId(
                userService.findByAuthUser().getId(),
                PageRequest.of(page, limit, Sort.by(sortBy).descending())
        );
    }

    @Override
    public PushProductApiConfig findById(long id) {
        Optional<PushProductApiConfig> optionalPushProductApiConfig = pushProductApiConfigRepository.findById(id);
        if(optionalPushProductApiConfig.isPresent()) {
            return optionalPushProductApiConfig.get();
        }
        return null;
    }

    @Override
    public PushProductApiConfig update(long id, PushProductApiConfig pushProductApiConfigUpdate) {
        Optional<PushProductApiConfig> optionalPushProductApiConfig = pushProductApiConfigRepository.findById(id);
        if(optionalPushProductApiConfig.isPresent()) {
            PushProductApiConfig pushProductApiConfig = optionalPushProductApiConfig.get();
            pushProductApiConfig.setApiUrl(pushProductApiConfigUpdate.getApiUrl());
            pushProductApiConfig.setHeaderConfig(pushProductApiConfigUpdate.getHeaderConfig());
            pushProductApiConfig.setName(pushProductApiConfigUpdate.getName());
            return pushProductApiConfigRepository.save(pushProductApiConfigUpdate);
        }
        return null;
    }

    @Override
    public PushProductApiConfig save(PushProductApiConfig pushProductApiConfig) {
        pushProductApiConfig.setUserId(userService.findByAuthUser().getId());
        return pushProductApiConfigRepository.save(pushProductApiConfig);
    }

    @Override
    public boolean deleteById(long id) {
        try {
            pushProductApiConfigRepository.deleteById(id);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
