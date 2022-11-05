package com.wolfnine.backend.restApi.client;

import com.wolfnine.backend.entity.dto.crawlConfigTemplate.SaveCrawlConfigTemplate;
import com.wolfnine.backend.service.crawlConfigTemplate.CrawlConfigTemplateService;
import com.wolfnine.backend.util.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/crawlerConfigTemplates")
@RequiredArgsConstructor
public class CrawlConfigTemplateController {
    private final CrawlConfigTemplateService crawlConfigTemplateService;

    @GetMapping
    public ResponseEntity<?> findAllByAuthUser() {
        return ResponseHandler.generateResponse(crawlConfigTemplateService.findAllByAuthUser());
    }

    @PostMapping
    public ResponseEntity<?> save( @Valid @RequestBody SaveCrawlConfigTemplate saveCrawlConfigTemplate) {
        return ResponseHandler.generateResponse(crawlConfigTemplateService.save(saveCrawlConfigTemplate));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable long id) {
        return ResponseHandler.generateResponse(crawlConfigTemplateService.findById(id));
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id,  @Valid @RequestBody SaveCrawlConfigTemplate saveCrawlConfigTemplate) {
        return ResponseHandler.generateResponse(crawlConfigTemplateService.update(id, saveCrawlConfigTemplate));
    }

    @GetMapping("/{id}/delete")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        return ResponseHandler.generateResponse(crawlConfigTemplateService.deleteById(id));
    }
}
