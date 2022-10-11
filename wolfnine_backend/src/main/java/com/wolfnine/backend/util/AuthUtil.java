package com.wolfnine.backend.util;

import com.github.dockerjava.api.exception.UnauthorizedException;
import com.wolfnine.backend.entity.User;
import com.wolfnine.backend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthUtil {
    private final UserService userService;

    public User getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            Optional<User> optionalUser = userService.findByUsername(currentUserName);
            if(optionalUser.isPresent()) {
                return optionalUser.get();
            }
        }
        throw new UnauthorizedException("Permission denied !");
    }
}
