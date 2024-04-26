package com.example.artconnect.controller;

import com.example.artconnect.service.user.ProfileInfo;
import com.example.artconnect.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final NativeWebRequest request;

    public UserController(UserService userService, NativeWebRequest request) {
        this.userService = userService;
        this.request = request;
    }

    @PutMapping("/profile/password")
    public ResponseEntity<Void> profilePassword(String newPassword) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean passwordChanged = userService.changeUserPassword(username, newPassword);
        if (passwordChanged) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/profile/avatar")
    public ResponseEntity<Void> updateAvatar(@RequestParam("file") MultipartFile file) throws IOException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean isUploaded = userService.saveUserAvatar(file, username);

        if (isUploaded) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PutMapping("/profile/username")
    public ResponseEntity<Void> profileUsername(String newUsername) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean usernameChanged = userService.changeUsername(username, newUsername);
        if (usernameChanged) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("/profile")
    public ResponseEntity<ProfileInfo> getProfile() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<ProfileInfo> profileInfo = userService.getProfileInfo(username);
        return profileInfo.map(info -> new ResponseEntity<>(info, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
