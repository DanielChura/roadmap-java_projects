package com.example.ToDoApp.controller;

import com.example.ToDoApp.dto.AuthResponse;
import com.example.ToDoApp.dto.RegisterRequest;
import com.example.ToDoApp.service.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // @PostMapping("/login")
    // public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request)
    // {
    // return ResponseEntity.status(HttpStatus.OK).body(authService.login(request));
    // }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.register(request));
    }
}