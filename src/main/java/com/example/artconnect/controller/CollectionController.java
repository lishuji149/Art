package com.example.artconnect.controller;

import com.example.artconnect.Entity.ArtCollection;
import com.example.artconnect.Entity.Artwork;
import com.example.artconnect.Entity.User;
import com.example.artconnect.service.collection.ArtCollectionService;
import com.example.artconnect.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/collections")
public class CollectionController {


    private final ArtCollectionService artCollectionService;
    private final UserService userService;
    private final NativeWebRequest request;

    public CollectionController(ArtCollectionService artCollectionService, UserService userService, NativeWebRequest request) {
        this.artCollectionService = artCollectionService;
        this.userService = userService;
        this.request = request;
    }

    @PostMapping("/create")
    public ResponseEntity<ArtCollection> createCollection(
            @RequestParam String name,
            @RequestParam String description) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        ArtCollection savedCollection = artCollectionService.createCollection(name, description, username);


        return ResponseEntity.ok(savedCollection);
    }

    @GetMapping("/{collectionId}/artworks")
    public ResponseEntity<List<Artwork>> getArtworksInCollection(@PathVariable Long collectionId) {
        List<Artwork> artworks = artCollectionService.getArtworksInCollection(collectionId);
        return ResponseEntity.ok(artworks);
    }

    @GetMapping("/latest")
    public ResponseEntity<List<ArtCollection>> getLatestCollections(
            @RequestParam(defaultValue = "10") int count) {
        List<ArtCollection> latestCollections = artCollectionService.getLatestCollections(count);
        return ResponseEntity.ok(latestCollections);
    }

    @GetMapping("/my-collections")
    public ResponseEntity<List<ArtCollection>> getUserCollections() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        // 假设你有一个方法通过用户名获取用户的实体或ID
        Optional<User> user = userService.findByUsername(username);

        List<ArtCollection> userCollections = artCollectionService.getUserCollections(user.get().getId());
        return ResponseEntity.ok(userCollections);

    }

    @PostMapping("/{collectionId}/artworks/{artworkId}")
    public ResponseEntity<?> addArtworkToCollection(@PathVariable Long collectionId, @PathVariable Long artworkId) {
        boolean success = artCollectionService.addArtworkToCollection(collectionId, artworkId);
        if (success) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{collectionId}")
    public ResponseEntity<?> deleteCollection(@PathVariable Long collectionId) {
        boolean isDeleted = artCollectionService.deleteCollection(collectionId);
        if (isDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{collectionId}")
    public ResponseEntity<?> updateCollection(
            @PathVariable Long collectionId,
            @RequestParam Optional<String> name,
            @RequestParam Optional<String> description) {


        boolean isUpdated = artCollectionService.updateCollection(collectionId, name, description);
        if (isUpdated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/{collectionId}")
    public ResponseEntity<ArtCollection> getCollectionById(@PathVariable Long collectionId) {
        Optional<ArtCollection> collection = artCollectionService.getCollectionById(collectionId);
        if (collection.isPresent()) {
            return ResponseEntity.ok(collection.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
