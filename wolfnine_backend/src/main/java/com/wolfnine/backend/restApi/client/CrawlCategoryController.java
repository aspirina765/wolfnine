package com.wolfnine.backend.restApi.client;

import com.wolfnine.backend.entity.dto.crawlCategory.CreateCrawlCategoryDto;
import com.wolfnine.backend.service.crawlCategory.CrawlCategoryService;
import com.wolfnine.backend.util.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;

@RestController
@RequestMapping(path = "/api/v1/crawler/categories")
@RequiredArgsConstructor
public class CrawlCategoryController {
    final CrawlCategoryService crawlCategoryService;

    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ) {
        return ResponseHandler.generateResponse(crawlCategoryService.findAllByAuthUser(limit, page, sortBy));
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody CreateCrawlCategoryDto createCrawlCategoryDto) {
        return ResponseHandler.generateResponse(crawlCategoryService.save(createCrawlCategoryDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable long id) {
        return ResponseHandler.generateResponse(crawlCategoryService.findById(id));
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @Valid @RequestBody CreateCrawlCategoryDto createCrawlCategoryDto) {
        return ResponseHandler.generateResponse(crawlCategoryService.update(id, createCrawlCategoryDto));
    }

    @GetMapping("/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable long id) {
        return ResponseHandler.generateResponse(crawlCategoryService.delete(id));
    }
}
