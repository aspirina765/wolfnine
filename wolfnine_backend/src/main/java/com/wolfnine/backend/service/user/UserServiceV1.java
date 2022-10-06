package com.wolfnine.backend.service.user;

import com.wolfnine.backend.entity.User;
import com.wolfnine.backend.entity.auth.Credential;
import com.wolfnine.backend.entity.dto.UserLoginDto;
import com.wolfnine.backend.entity.dto.UserRegisterDto;
import com.wolfnine.backend.entity.entityEnum.UserStatus;
import com.wolfnine.backend.repository.UserRepository;
import com.wolfnine.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceV1 implements UserService{
    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;

    @Override
    public UserRegisterDto register(UserRegisterDto userRegisterDto) {
        Optional<User> optionalUser = userRepository.findUserByUsername(userRegisterDto.getUsername());
        if (optionalUser.isPresent()) {
            return null;
        }
        User user = User.builder()
                .name(userRegisterDto.getFullName())
                .email(userRegisterDto.getEmail())
                .phone(userRegisterDto.getPhone())
                .username(userRegisterDto.getUsername())
                .status(UserStatus.ACTIVE)
                .password(passwordEncoder.encode(userRegisterDto.getPassword()))
                .build();
        userRepository.save(user);
        UserRegisterDto userDto = UserRegisterDto.builder()
                .fullName(userRegisterDto.getFullName())
                .email(userRegisterDto.getEmail())
                .phone(userRegisterDto.getPhone())
                .avatar(userRegisterDto.getAvatar())
                .username(userRegisterDto.getUsername())
                .id(user.getId())
                .build();
        return userDto;
    }

    @Override
    public Credential login(UserLoginDto userLoginDto) {
        User user = (User) loadUserByUsername(userLoginDto.getUsername());
        boolean isMatched = passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword());
        Optional<User> optionalUser = userRepository.findUserByUsername(userLoginDto.getUsername());
        if(optionalUser.isPresent()) {
            User optionUser = optionalUser.get();
            int expiredAfterDay = 7;
            String accessToken = JwtUtil.generateTokenV2(optionUser, expiredAfterDay );
            String refreshToken = JwtUtil.generateTokenV2(optionUser, 14);
            return Credential.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .expiredAt(System.currentTimeMillis() + expiredAfterDay * 24 * 60 * 60 * 60)
                    .scope("basic_user_info")
                    .build();
        }
        throw new UsernameNotFoundException("User not found");
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findUserByUsername(username);
        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }
        User user = optionalUser.get();
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ADMIN");
        grantedAuthorityList.add(simpleGrantedAuthority);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorityList);
    }
}
