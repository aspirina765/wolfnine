package com.wolfnine.backend.restApi;

import com.wolfnine.backend.entity.dto.UserRegisterDto;
import com.wolfnine.backend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return ResponseEntity.ok(userService.register(userRegisterDto));
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
}
