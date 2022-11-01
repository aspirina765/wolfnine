package com.wolfnine.backend.restApi.client;

import com.wolfnine.backend.entity.dto.crawlConfig.SaveCrawlConfigDto;
import com.wolfnine.backend.service.crawlConfig.CrawlConfigService;
import com.wolfnine.backend.util.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/crawler/configs")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CrawlConfigController {
    private final CrawlConfigService crawlConfigService;

    @GetMapping
    public ResponseEntity<?> findAllByAuthUser(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ) {
        return ResponseHandler.generateResponse(crawlConfigService.findByAuthUser(limit, page, sortBy));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findAllByAuthUser(@PathVariable long id) {
        return ResponseHandler.generateResponse(crawlConfigService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody SaveCrawlConfigDto saveCrawlConfigDto) {
        return ResponseHandler.generateResponse(crawlConfigService.save(saveCrawlConfigDto));
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @Valid @RequestBody SaveCrawlConfigDto saveCrawlConfigDto) {
        return ResponseHandler.generateResponse(crawlConfigService.update(id, saveCrawlConfigDto));
    }

    @GetMapping("/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable long id) {
        return ResponseHandler.generateResponse(crawlConfigService.delete(id));
    }
}
