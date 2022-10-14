package com.wolfnine.backend.restApi;

import com.wolfnine.backend.service.user.UserService;
import com.wolfnine.backend.util.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    final UserService userService;

    @GetMapping(path = "/my-info")
    public ResponseEntity<?> getMyInfo() {
        return ResponseHandler.generateResponse(userService.findByAuthUser());
    }
}
