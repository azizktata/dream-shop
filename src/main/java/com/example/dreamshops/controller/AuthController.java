package com.example.dreamshops.controller;

import com.example.dreamshops.dto.AuthDto;
import com.example.dreamshops.dto.UserDto;
import com.example.dreamshops.model.User;
import com.example.dreamshops.request.LoginRequest;
import com.example.dreamshops.request.UserRequest;
import com.example.dreamshops.service.Auth.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public UserDto register(@RequestBody UserRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthDto login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
