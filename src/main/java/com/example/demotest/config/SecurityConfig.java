package com.example.demotest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests()
                .antMatchers(HttpMethod.POST, "/api/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/charging-stations").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/charging-stations/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/charging-stations/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/type/charging-stations").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/type/charging-stations/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/type/charging-stations/**").permitAll()
                .antMatchers(HttpMethod.POST, "/").permitAll()
                .antMatchers("/").permitAll();
        return httpSecurity.build();
    }
}
