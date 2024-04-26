package com.example.artconnect.service.artwork;


import com.example.artconnect.Entity.Artwork;
import com.example.artconnect.Entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ArtworkService {
    Artwork uploadArtwork(User user, String title, String description, MultipartFile file) throws IOException;

    Optional<Artwork> findById(Long id);
    void deleteArtwork(Artwork artwork);
    List<Artwork> findLatestArtworks();

}
