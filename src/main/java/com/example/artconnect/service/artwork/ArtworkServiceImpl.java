package com.example.artconnect.service.artwork;

import com.example.artconnect.Entity.Artwork;
import com.example.artconnect.Entity.User;
import com.example.artconnect.repository.ArtworkRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ArtworkServiceImpl implements ArtworkService{

    private final FileStorageService fileStorageService;
    final private ArtworkRepository artworkRepository;

    public ArtworkServiceImpl(FileStorageService fileStorageService, ArtworkRepository artworkRepository) {
        this.fileStorageService = fileStorageService;
        this.artworkRepository = artworkRepository;
    }

    public Artwork uploadArtwork(User user, String title, String description, MultipartFile file) throws IOException {

        String mediaUrl = fileStorageService.storeFile(file);


        Artwork artwork = new Artwork();
        artwork.setUser(user);
        artwork.setTitle(title);
        artwork.setDescription(description);
        artwork.setMediaURL(mediaUrl);
        artwork.setUploadDate(LocalDateTime.now());

        return artworkRepository.save(artwork);
    }

    public Optional<Artwork> findById(Long id) {
        return artworkRepository.findById(id);
    }

    public void deleteArtwork(Artwork artwork) {
        artworkRepository.delete(artwork);
    }

    public List<Artwork> findLatestArtworks() {

        return artworkRepository.findTop10ByOrderByUploadDateDesc();
    }



}
