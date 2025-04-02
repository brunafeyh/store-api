package com.example.eventos_api.domain.controller;

import com.example.eventos_api.domain.user.User;
import com.example.eventos_api.domain.user.UserLoginDTO;
import com.example.eventos_api.domain.user.UserRegistrationDTO;
import com.example.eventos_api.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register/client")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerClient(@RequestBody UserRegistrationDTO registrationDTO) {
        return userService.registerClient(registrationDTO);
    }

    @PostMapping("/register/employee")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerEmployee(@RequestHeader("Authorization") String adminToken,
                                 @RequestBody UserRegistrationDTO registrationDTO) {
        return userService.registerEmployee(registrationDTO, adminToken);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserLoginDTO loginDTO) {
        return userService.login(loginDTO);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@RequestHeader("Authorization") String token) {
        userService.logout(token);
    }
}
