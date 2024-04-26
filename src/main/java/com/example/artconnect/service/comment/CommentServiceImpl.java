package com.example.artconnect.service.comment;

import com.example.artconnect.Entity.Artwork;
import com.example.artconnect.Entity.Comment;
import com.example.artconnect.Entity.User;
import com.example.artconnect.repository.ArtworkRepository;
import com.example.artconnect.repository.CommentRepository;
import com.example.artconnect.service.artwork.FileStorageService;
import com.example.artconnect.service.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final UserService userService;

    private final FileStorageService fileStorageService;


    private final ArtworkRepository artworkRepository;

    public CommentServiceImpl(CommentRepository commentRepository, UserService userService, FileStorageService fileStorageService, ArtworkRepository artworkRepository) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.fileStorageService = fileStorageService;
        this.artworkRepository = artworkRepository;
    }

    public Comment addCommentToArtwork(Long artworkId, String content, MultipartFile image, String username) throws IOException {
        Optional<User> user = userService.findByUsername(username);
        Optional<Artwork> artworkOptional = artworkRepository.findById(artworkId);

        if (!artworkOptional.isPresent() || !user.isPresent()) {
            throw new IllegalArgumentException("Artwork or User not found");
        }

        Comment comment = new Comment();
        comment.setCommentText(content);
        comment.setUser(user.get());
        comment.setArtwork(artworkOptional.get());

        if (image != null && !image.isEmpty()) {
            String imageUrl = fileStorageService.storeFile(image);
            comment.setImageUrl(imageUrl);
        }

        comment.setCommentDate(LocalDateTime.now());
        return commentRepository.save(comment);
    }


    public List<Comment> getCommentsByArtwork(Long artworkId) {
        return commentRepository.findByArtworkArtworkId(artworkId);
    }
}
