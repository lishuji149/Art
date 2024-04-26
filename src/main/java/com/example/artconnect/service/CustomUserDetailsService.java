package com.example.artconnect.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import com.example.artconnect.Entity.User;


public interface CustomUserDetailsService extends UserDetailsService {
    User getUserById(Long id);
}