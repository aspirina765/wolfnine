package com.wolfnine.backend.restApi.client;

import com.wolfnine.backend.entity.User;
import com.wolfnine.backend.service.product.ProductService;
import com.wolfnine.backend.service.user.UserService;
import com.wolfnine.backend.util.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    final ProductService productService;

    @GetMapping
    public ResponseEntity<?> findAllByAuthUser(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ) {
        return ResponseHandler.generateResponse(productService.findAllByAuthUser(limit, page, sortBy));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        return ResponseHandler.generateResponse(productService.findById(id));
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteAllByIdIn(@RequestBody List<Long> ids) {
        return ResponseHandler.generateResponse(productService.deleteAllByIdIn(ids));
    }

    @GetMapping("/{id}/delete")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        return ResponseHandler.generateResponse(productService.deleteById(id));
    }
}
