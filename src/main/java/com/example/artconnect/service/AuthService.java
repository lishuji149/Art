package com.example.artconnect.service;


import com.example.artconnect.Entity.User;

public interface AuthService {
    TokenPair login(String username, String password);
    User register(String username, String password,String Email);
    TokenPair refresh(String refreshToken);
}