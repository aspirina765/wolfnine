package com.wolfnine.backend.restApi;

import com.wolfnine.backend.entity.CrawlConfig;
import com.wolfnine.backend.service.crawlConfig.CrawlConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/crawl-configs")
public class CrawlConfigController {
    private final CrawlConfigService crawlConfigService;

    @GetMapping
    public ResponseEntity<List<CrawlConfig>> findAllByUserId(@RequestParam long userId) {
        return ResponseEntity.ok(crawlConfigService.findByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody CrawlConfig crawlConfig) {
        return ResponseEntity.ok(crawlConfigService.save(crawlConfig));
    }
}
