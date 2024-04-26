package com.example.artconnect.service.user;

import com.example.artconnect.Entity.ArtCollection;
import com.example.artconnect.Entity.User;
import com.example.artconnect.repository.UserRepository;
import com.example.artconnect.service.artwork.FileStorageService;
import com.example.artconnect.service.collection.CollectionDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final FileStorageService fileStorageService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, FileStorageService fileStorageService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.fileStorageService = fileStorageService;
    }

    @Override
    @Transactional
    public boolean changeUserPassword(String username, String newPassword) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            return false;
        }
        User user = userOptional.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }

    @Override
    @Transactional
    public boolean changeUsername(String username, String newUsername) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            return false;
        }
        if (userRepository.existsByUsername(newUsername)) {
            return false;
        }
        User user = userOptional.get();
        user.setUsername(newUsername);
        userRepository.save(user);
        return true;
    }

    public boolean saveUserAvatar (MultipartFile avatar, String username) throws IOException {
        String mediaUrl = fileStorageService.storeFile(avatar);
        return userRepository.findByUsername(username)
                .map(user -> {
                    user.setAvatarUrl(mediaUrl);
                    userRepository.save(user);
                    return true;
                })
                .orElse(false);
    }

    public Optional<ProfileInfo> getProfileInfo(String username) {
        return userRepository.findByUsername(username)
                .map(user -> {
                    List<CollectionDTO> collectionDTOs = user.getCollections().stream()
                            .map(this::convertToCollectionDTO)
                            .collect(Collectors.toList());

                    return new ProfileInfo(user.getAvatarUrl(),user.getUsername(), user.getEmail(), collectionDTOs);
                });
    }

    private CollectionDTO convertToCollectionDTO(ArtCollection artCollection) {
        return new CollectionDTO(
                artCollection.getCollectionId(),
                artCollection.getName(),
                artCollection.getDescription(),
                artCollection.getCreationDate()
                // 你可以继续添加其他属性
        );
    }

    @Override
    public Optional<User> findByUsername(String username) {

        return userRepository.findByUsername(username);
    }



    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

}
