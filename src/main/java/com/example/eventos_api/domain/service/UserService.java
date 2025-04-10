package com.example.eventos_api.domain.service;
import com.example.eventos_api.domain.user.*;
import com.example.eventos_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;

    @Autowired
    public UserService(UserRepository userRepository, JwtTokenService jwtTokenService) {
        this.userRepository = userRepository;
        this.jwtTokenService = jwtTokenService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public void registerClient(UserRegistrationDTO dto) {
        if (!dto.role().equalsIgnoreCase("CLIENT")) {
            throw new RuntimeException("Somente usuários do tipo CLIENT podem se registrar por si mesmos");
        }
        registerUser(dto);
    }

    public void registerEmployee(UserRegistrationDTO dto, String adminToken) {
        User adminUser = getUserByToken(adminToken);
        if (!adminUser.getRole().toString().equalsIgnoreCase("ADMIN")) {
            throw new RuntimeException("Apenas administradores podem criar contas de funcionário");
        }
        if (!dto.role().equalsIgnoreCase("EMPLOYEE")) {
            throw new RuntimeException("Para criação de funcionário, o role deve ser EMPLOYEE");
        }
        registerUser(dto);
    }

    private void registerUser(UserRegistrationDTO dto) {
        Optional<User> existingUser = userRepository.findByEmail(dto.email());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }
        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        try {
            user.setRole(Role.valueOf(dto.role().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Role inválido. Valores permitidos: CLIENT, EMPLOYEE, ADMIN");
        }
        userRepository.save(user);
    }

    public RecoveryJwtTokenDto authenticateUser(UserLoginDTO dto) {
        Optional<User> userOpt = userRepository.findByEmail(dto.email());
        if (userOpt.isEmpty() || !passwordEncoder.matches(dto.password(), userOpt.get().getPassword())) {
            throw new RuntimeException("Email ou senha inválidos");
        }
        User user = userOpt.get();
        String jwt = jwtTokenService.generateToken(user);
        return new RecoveryJwtTokenDto(jwt);
    }


    public User getUserByToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove o "Bearer "
        }

        String userId = jwtTokenService.getSubjectFromToken(token);
        return userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado para o token informado"));
    }


    public List<User> listAllClients() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRole() == Role.CLIENT)
                .collect(Collectors.toList());
    }
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

    public List<User> listAllEmployees() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRole() == Role.EMPLOYEE)
                .collect(Collectors.toList());
    }


    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
