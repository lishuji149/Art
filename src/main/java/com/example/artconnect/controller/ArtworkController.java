package com.example.artconnect.controller;

import com.example.artconnect.Entity.Artwork;
import com.example.artconnect.Entity.User;
import com.example.artconnect.service.artwork.ArtworkService;
import com.example.artconnect.service.user.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/artwork")
public class ArtworkController {



    private final ArtworkService artworkService;
    private final NativeWebRequest request;
    private final UserService userService;

    public ArtworkController(ArtworkService artworkService, NativeWebRequest request, UserService userService) {
        this.artworkService = artworkService;
        this.request = request;
        this.userService = userService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Artwork> uploadArtwork(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestPart("file") MultipartFile file) throws IOException {

        User user = getCurrentUser();

        Artwork artwork = artworkService.uploadArtwork(user, title, description, file);

        if (artwork != null) {
            return new ResponseEntity<>(artwork, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteArtwork(Long artworkId) {
        User currentUser = getCurrentUser();


        Optional<Artwork> artwork = artworkService.findById(artworkId);
        if (!artwork.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        if (!artwork.get().getUser().equals(currentUser)) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: Unauthorized deletion attempt.");
        }

        artworkService.deleteArtwork(artwork.get());
        return ResponseEntity.ok().body("Artwork deleted successfully.");
    }

    @GetMapping("/latest")
    public ResponseEntity<List<Artwork>> getLatestArtworks() {

        List<Artwork> latestArtworks = artworkService.findLatestArtworks();
        if (latestArtworks != null && !latestArtworks.isEmpty()) {
            return ResponseEntity.ok(latestArtworks);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userService.findByUsername(username);

        return user.orElse(null);
    }


}
