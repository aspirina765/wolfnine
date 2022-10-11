package com.wolfnine.backend.restApi;

import com.wolfnine.backend.entity.dto.crawlConfig.SaveCrawlConfigDto;
import com.wolfnine.backend.service.crawlConfig.CrawlConfigService;
import com.wolfnine.backend.util.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/crawler/configs")
@CrossOrigin("*")
@RequiredArgsConstructor
public class CrawlConfigController {
    private final CrawlConfigService crawlConfigService;

    @GetMapping
    public ResponseEntity<?> findAllByAuthUser() {
        return ResponseHandler.generateResponse(crawlConfigService.findByAuthUser());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findAllByAuthUser(@PathVariable long id) {
        return ResponseHandler.generateResponse(crawlConfigService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody SaveCrawlConfigDto saveCrawlConfigDto) {
        return ResponseHandler.generateResponse(crawlConfigService.save(saveCrawlConfigDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @Valid @RequestBody SaveCrawlConfigDto saveCrawlConfigDto) {
        return ResponseHandler.generateResponse(crawlConfigService.update(id, saveCrawlConfigDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        return ResponseEntity.ok().build();
    }
}
