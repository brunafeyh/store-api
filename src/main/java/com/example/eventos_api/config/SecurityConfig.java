package com.example.eventos_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Desabilite o CSRF se não for necessário (cuidado em produção)
                .authorizeHttpRequests(authorize -> authorize
                        // Permite acesso público aos endpoints de autenticação
                        .requestMatchers("/auth/register/**", "/auth/login").permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}
