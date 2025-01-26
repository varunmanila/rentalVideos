package com.example.demo.service;

import com.example.demo.controller.exchanges.request.AuthRequest;
import com.example.demo.controller.exchanges.request.RegisterRequest;
import com.example.demo.controller.exchanges.response.AuthResponse;
import com.example.demo.model.User;
import com.example.demo.model.enums.Role;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JWTService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (request.getRole() == null) {
            request.setRole(Role.USER);
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
        userRepository.save(user);
        return AuthResponse.builder()
            .accessToken(jwtToken)
            .build();

    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail());
        String jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .accessToken(jwtToken)
                .build();
}
}
