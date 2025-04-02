package com.example.eventos_api.domain.controller;

import com.example.eventos_api.domain.user.User;
import com.example.eventos_api.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/clients")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<User>> getAllClients() {
        List<User> clients = userService.listAllClients();
        return ResponseEntity.ok(clients);
    }

}
