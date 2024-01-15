package com.alura.forum.infra.security;

import static com.alura.forum.constants.Constants.PUBLIC_URLS;
import static com.alura.forum.constants.Constants.DELETE_USER_PATH;
import static com.alura.forum.constants.Constants.ROLE_ADMIN;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private SecurityFilter securityFilter;
    @Autowired
    private AuthenticationProvider authProvider;
    @Autowired
    private HttpEndpointChecker endpointChecker;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint(endpointChecker))
                .and()
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler(endpointChecker))
                .and()
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authRequest -> authRequest.requestMatchers(PUBLIC_URLS).permitAll()
                        .requestMatchers(HttpMethod.DELETE, DELETE_USER_PATH).hasAuthority(ROLE_ADMIN)
                    .anyRequest().authenticated()
                )
                .sessionManagement(sessionManager->sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
