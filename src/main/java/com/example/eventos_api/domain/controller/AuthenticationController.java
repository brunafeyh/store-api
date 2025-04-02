package com.example.eventos_api.domain.controller;
import com.example.eventos_api.domain.service.UserService;
import com.example.eventos_api.domain.user.RecoveryJwtTokenDto;
import com.example.eventos_api.domain.user.UserLoginDTO;
import com.example.eventos_api.domain.user.UserRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> registerClient(@RequestBody UserRegistrationDTO createUserDto) {
        userService.registerClient(createUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/register/employee")
    public ResponseEntity<?> registerEmployee(@RequestHeader("Authorization") String adminToken,
                                              @RequestBody UserRegistrationDTO createUserDto) {
        userService.registerEmployee(createUserDto, adminToken);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<RecoveryJwtTokenDto> login(@RequestBody UserLoginDTO loginUserDto) {
        RecoveryJwtTokenDto tokenDto = userService.authenticateUser(loginUserDto);
        return ResponseEntity.ok(tokenDto);
    }

}
