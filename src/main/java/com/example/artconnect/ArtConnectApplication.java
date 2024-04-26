package com.example.artconnect;

import com.example.artconnect.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.artconnect.Entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ArtConnectApplication  {



    public static void main(String[] args) {
        SpringApplication.run(ArtConnectApplication.class, args);
    }


}
