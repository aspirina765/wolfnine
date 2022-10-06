package com.wolfnine.backend.service.user;

import com.wolfnine.backend.entity.User;
import com.wolfnine.backend.entity.auth.Credential;
import com.wolfnine.backend.entity.dto.UserLoginDto;
import com.wolfnine.backend.entity.dto.UserRegisterDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService extends UserDetailsService {
    UserRegisterDto register(UserRegisterDto userRegisterDto);
    Credential login(UserLoginDto userLoginDto);
    Optional<User> findByUsername(String username);
}
