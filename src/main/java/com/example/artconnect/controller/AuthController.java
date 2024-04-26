package com.example.artconnect.controller;

import com.example.artconnect.Entity.User;
import com.example.artconnect.service.AuthService;
import com.example.artconnect.service.TokenPair;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final NativeWebRequest request;

    public AuthController(AuthService authService, NativeWebRequest request) {
        this.authService = authService;
        this.request = request;
    }

    @PostMapping("/login")
    public TokenPair login(@RequestParam String username, @RequestParam String password) {
        return authService.login(username, password);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registration(@RequestBody @Valid User user) {
        System.out.println(user);
        User registeredUser = authService.register(user.getUsername(), user.getPassword(), user.getEmail());
        if (registeredUser != null) {
            return ResponseEntity.ok("User registered successfully");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username is already taken");
        }
    }

    @GetMapping("/refresh")
    public Object refresh(@RequestParam String refreshToken) {
        TokenPair pair = authService.refresh(refreshToken);
        if (pair == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return pair;
    }
}