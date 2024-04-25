package com.example.todo.application.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import com.example.todo.application.security.JwtTokenFilter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;
    public WebSecurityConfig (JwtTokenFilter jwtTokenFilter){
        this.jwtTokenFilter=jwtTokenFilter;
    }
    public static class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"); // Customize error response
        }
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.OPTIONS).permitAll() // Allow preflight requests
                                .requestMatchers("/api/users/login", "/api/users/signup").permitAll()
                                .requestMatchers("/projects/**", "/task/**","/api/export/**").authenticated()
                                .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/users/login", "/api/users/signup", "/projects/**", "/task/**","/api/export/**"))
                .cors().configurationSource(request -> { // Configure CORS using a lambda expression
                    CorsConfiguration corsConfig = new CorsConfiguration();
                    corsConfig.setAllowedOrigins(Collections.singletonList("http://127.0.0.1:5173")); // Replace with your allowed origin(s)
                    corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Allowed methods
                    corsConfig.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization", "X-Requested-With")); // Allowed headers
                    corsConfig.setAllowCredentials(true);
                    return corsConfig;
                });

        return http.build();
    }
}
