package com.wolfnine.backend.restApi;

import com.github.dockerjava.api.exception.UnauthorizedException;
import com.wolfnine.backend.entity.User;
import com.wolfnine.backend.service.product.ProductService;
import com.wolfnine.backend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    final ProductService productService;
    final UserService userService;

    @GetMapping
    public ResponseEntity<?> findAll(
            Authentication authentication
    ) {
        Optional<User> optionalUser = userService.findByUsername(authentication.getName());
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            return ResponseEntity.ok(productService.findByUserId(user.getId()));
        }
        return ResponseEntity.notFound().build();
    }
}
