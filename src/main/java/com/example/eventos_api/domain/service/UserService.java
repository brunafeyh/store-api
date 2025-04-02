package com.example.eventos_api.domain.service;

import com.example.eventos_api.domain.user.*;
import com.example.eventos_api.repositories.UserRepository;
import com.example.eventos_api.repositories.UserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserTokenRepository userTokenRepository) {
        this.userRepository = userRepository;
        this.userTokenRepository = userTokenRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // Método auxiliar para obter o usuário a partir do token
    public User getUserByToken(String token) {
        Optional<UserToken> tokenOpt = userTokenRepository.findByToken(token);
        if (tokenOpt.isEmpty() || tokenOpt.get().getExpiresAt().before(new Date())) {
            throw new RuntimeException("Token expirado ou inválido");
        }
        return tokenOpt.get().getUser();
    }

    // Registra um cliente (self-service)
    public User registerClient(UserRegistrationDTO dto) {
        if (!dto.role().equalsIgnoreCase("CLIENT")) {
            throw new RuntimeException("Somente usuários do tipo CLIENT podem se registrar por si mesmos");
        }
        return registerUser(dto);
    }

    // Registra um funcionário (somente admin pode criar)
    public User registerEmployee(UserRegistrationDTO dto, String adminToken) {
        // Verifica se o token fornecido pertence a um administrador
        User adminUser = getUserByToken(adminToken);
        if (!adminUser.getRole().toString().equalsIgnoreCase("ADMIN")) {
            throw new RuntimeException("Apenas administradores podem criar contas de funcionário");
        }
        if (!dto.role().equalsIgnoreCase("EMPLOYEE")) {
            throw new RuntimeException("Para criação de funcionário, o role deve ser EMPLOYEE");
        }
        return registerUser(dto);
    }

    // Registro comum de usuário
    private User registerUser(UserRegistrationDTO dto) {
        Optional<User> existingUser = userRepository.findByEmail(dto.email());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }
        User user = new User();
        // Utilizamos o campo email
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        try {
            user.setRole(Role.valueOf(dto.role().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Role inválido. Valores permitidos: CLIENT, EMPLOYEE, ADMIN");
        }
        return userRepository.save(user);
    }

    // Login: valida credenciais e gera token
    public String login(UserLoginDTO dto) {
        Optional<User> userOpt = userRepository.findByEmail(dto.email());
        if (userOpt.isEmpty() || !passwordEncoder.matches(dto.password(), userOpt.get().getPassword())) {
            throw new RuntimeException("Email ou senha inválidos");
        }
        User user = userOpt.get();
        String token = UUID.randomUUID().toString();
        Date expiresAt = new Date(System.currentTimeMillis() + 3600 * 1000); // Token válido por 1 hora
        UserToken userToken = new UserToken();
        userToken.setUser(user);
        userToken.setToken(token);
        userToken.setExpiresAt(expiresAt);
        userTokenRepository.save(userToken);
        return token;
    }

    // Logout: remove o token da base
    public void logout(String token) {
        Optional<UserToken> tokenOpt = userTokenRepository.findByToken(token);
        tokenOpt.ifPresent(userTokenRepository::delete);
    }

    // Outros métodos (listagem, atualização, etc.) podem ser adicionados conforme necessário
    public List<User> listAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(UUID id, UserRegistrationDTO dto) {
        User user = getUserById(id);
        user.setEmail(dto.email());
        if (dto.password() != null && !dto.password().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.password()));
        }
        try {
            user.setRole(Role.valueOf(dto.role().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Role inválido. Valores permitidos: CLIENT, EMPLOYEE, ADMIN");
        }
        return userRepository.save(user);
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
