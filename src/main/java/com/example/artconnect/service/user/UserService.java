package com.example.artconnect.service.user;

import com.example.artconnect.Entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface UserService {
    boolean changeUserPassword(String username, String newPassword);

    boolean changeUsername(String username, String newUsername);

    Optional<ProfileInfo> getProfileInfo(String username);

    Optional<User> findByUsername(String username);

    boolean saveUserAvatar (MultipartFile avatar, String username) throws IOException;

    Optional<User> findById(Long id);
}
