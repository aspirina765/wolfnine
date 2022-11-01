package com.wolfnine.backend.restApi.client;

import com.wolfnine.backend.entity.PushProductApiConfig;
import com.wolfnine.backend.service.pushProductApiConfig.PushProductApiConfigService;
import com.wolfnine.backend.util.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/pushProductApis")
@RequiredArgsConstructor
public class PushProductApiConfigController {
    private final PushProductApiConfigService pushProductApiConfigService;

    @GetMapping
    public ResponseEntity<?> findAllByAuthUser(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ) {
        return ResponseHandler.generateResponse(pushProductApiConfigService.findAllByAuthUser(page, limit, sortBy));
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody PushProductApiConfig pushProductApiConfig) {
        return ResponseHandler.generateResponse(pushProductApiConfigService.save(pushProductApiConfig));
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @Valid @RequestBody PushProductApiConfig pushProductApiConfig) {
        return ResponseHandler.generateResponse(pushProductApiConfigService.update(id, pushProductApiConfig));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable long id) {
        return ResponseHandler.generateResponse(pushProductApiConfigService.findById(id));
    }

    @GetMapping("/{id}/delete")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        return ResponseHandler.generateResponse(pushProductApiConfigService.deleteById(id));
    }
}
