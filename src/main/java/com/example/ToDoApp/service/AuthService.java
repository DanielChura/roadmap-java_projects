package com.example.ToDoApp.service;

import com.example.ToDoApp.dto.AuthResponse;
import com.example.ToDoApp.dto.RegisterRequest;
import com.example.ToDoApp.entity.User;
import com.example.ToDoApp.jwt.JwtUtil;
import com.example.ToDoApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    // public AuthResponse login(LoginRequest request) {
    // }

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Usuario ya registrado");
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        userRepository.save(user);

        String token = jwtUtil.generateToken(user);
        return new AuthResponse(token);
    }
}
