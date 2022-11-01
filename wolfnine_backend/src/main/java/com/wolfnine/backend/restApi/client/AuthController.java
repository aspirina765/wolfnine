package com.wolfnine.backend.restApi.client;

import com.wolfnine.backend.entity.dto.UserRegisterDto;
import com.wolfnine.backend.service.user.UserService;
import com.wolfnine.backend.util.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/users")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AuthController {
    final UserService userService;

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDto userRegisterDto) {
        return ResponseHandler.generateResponse(userService.register(userRegisterDto));
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName()  + " "
                    + violation.getPropertyPath() + ": "
                    + violation.getMessage());
        }
        return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(Authentication authentication) {
        authentication.setAuthenticated(false);
        SecurityContextHolder.getContext().setAuthentication(null);
        return ResponseHandler.generateResponse(true);
    }
}
