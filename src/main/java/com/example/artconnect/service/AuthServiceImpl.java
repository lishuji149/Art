package com.example.artconnect.service;

import java.util.Optional;

import com.example.artconnect.Entity.User;
import com.example.artconnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           JwtService jwtService,
                           UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public TokenPair login(String username, String password) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        User user = (User) auth.getPrincipal();
        return jwtService.generateTokenPair(user);
    }

    @Override
    public User register(String username, String password,String Email) {
        if (userRepository.findByUsername(username).isEmpty()) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setEmail(Email);
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public TokenPair refresh(String refreshToken) {
        Long userId = jwtService.getUserIdFromRefresh(refreshToken);
        if (userId != null) {
            Optional<User> user = userRepository.findById(userId);
            return user.map(jwtService::generateTokenPair).orElse(null);
        }
        return null;
    }
}