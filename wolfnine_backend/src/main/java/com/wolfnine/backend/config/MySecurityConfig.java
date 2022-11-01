package com.wolfnine.backend.config;

import com.wolfnine.backend.filter.MyAuthenticationFilter;
import com.wolfnine.backend.filter.MyAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
    final UserDetailsService accountService;
    final PasswordEncoder passwordEncoder;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
//        http.cors().configurationSource(c -> {
//            CorsConfiguration corsCfg = new CorsConfiguration();
//
//            // All origins, or specify the origins you need
//            corsCfg.addAllowedOriginPattern( "*" );
//            corsCfg.addAllowedOrigin("*");
//
//            // If you really want to allow all methods
////            corsCfg.addAllowedMethod( CorsConfiguration.ALL );
//
//            // If you want to allow specific methods only
//             corsCfg.addAllowedMethod( HttpMethod.GET );
//             corsCfg.addAllowedMethod( HttpMethod.DELETE );
//             corsCfg.addAllowedMethod( HttpMethod.PUT );
//             corsCfg.addAllowedMethod( HttpMethod.POST );
//            // ...
//            return corsCfg;
//        });
        MyAuthenticationFilter authenticationFilter = new MyAuthenticationFilter(authenticationManagerBean());
        authenticationFilter.setFilterProcessesUrl("/api/v1/users/login");
        http
                .logout(logout -> logout
                        .logoutUrl("/api/v1/users/logout")
                        .addLogoutHandler(new SecurityContextLogoutHandler())
                        .invalidateHttpSession(true)
                );
        http.authorizeHttpRequests().antMatchers("/api/v1/users/register").permitAll();
        http.authorizeHttpRequests()
                .antMatchers(
                        "/api/v1/products/**",
                        "/api/v1/crawler/**",
                        "/api/v1/shopeeShopConfigs/**",
                        "/api/v1/pushProductApis/**",
                        "/api/v1/users/my-info"
                )
                .hasAnyAuthority("USER", "ADMIN");
        http
                .addFilterBefore(new MyAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests()
                .antMatchers("/api/v1/admin/*")
                .hasAnyAuthority("ADMIN");

        http.addFilter(authenticationFilter);
    }
}
