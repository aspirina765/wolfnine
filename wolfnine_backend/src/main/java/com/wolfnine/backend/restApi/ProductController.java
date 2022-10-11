package com.wolfnine.backend.restApi;

import com.wolfnine.backend.entity.User;
import com.wolfnine.backend.service.product.ProductService;
import com.wolfnine.backend.service.user.UserService;
import com.wolfnine.backend.util.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    final ProductService productService;
    final UserService userService;

    @GetMapping
    public ResponseEntity<?> findAllByAuthUser(
            Authentication authentication
    ) {
        Optional<User> optionalUser = userService.findByUsername(authentication.getName());
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            return ResponseHandler.generateResponse(productService.findByUserId(user.getId()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Permission denied !");
    }
}
