package com.example.eventos_api.domain.user;

public record UserRegistrationDTO(
        String name,
        String email,
        String password,
        String role
) {}
