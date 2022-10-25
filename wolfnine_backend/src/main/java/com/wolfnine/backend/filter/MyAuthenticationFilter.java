package com.wolfnine.backend.filter;

import com.google.gson.Gson;
import com.wolfnine.backend.entity.UserService;
import com.wolfnine.backend.entity.auth.Credential;
import com.wolfnine.backend.entity.dto.UserLoginDto;
import com.wolfnine.backend.service.user.UserServiceV1;
import com.wolfnine.backend.util.JwtUtil;
import com.wolfnine.backend.util.ResponseHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class MyAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            String jsonData = request.getReader().lines().collect(Collectors.joining());
            Gson gson = new Gson();
            UserLoginDto userLoginDto = gson.fromJson(jsonData, UserLoginDto.class);
            UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(
                    userLoginDto.getUsername(), userLoginDto.getPassword()
            );
            return authenticationManager.authenticate(userToken);
        }catch (IOException ex) {
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        int expiredAfterDay = 7;
        String accessToken = JwtUtil.generateToken(
                user.getUsername(),
                user.getAuthorities().iterator().next().getAuthority(),
                request.getRequestURI().toString()
                , expiredAfterDay * 24 * 60 * 60 * 60);
        String refreshToken = JwtUtil.generateToken(
                user.getUsername(),
                user.getAuthorities().iterator().next().getAuthority(),
                request.getRequestURI().toString()
                , 14 * 24 * 60 * 60 * 60);
        Credential credential = Credential.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiredAt(System.currentTimeMillis() + expiredAfterDay * 24 * 60 * 60 * 60)
                .scope("basic_account_info")
                .build();
        Gson gson = new Gson();
        response.setContentType("application/json");
        response.getWriter().print(gson.toJson(ResponseHandler.generateResponse(credential).getBody()));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        HashMap<String, String> errors = new HashMap<>();
        errors.put("message", failed.getMessage());
        response.setContentType("application/json");
        Gson gson = new Gson();
        response.getWriter().print(gson.toJson(errors));
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
