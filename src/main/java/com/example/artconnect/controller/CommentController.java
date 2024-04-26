package com.example.artconnect.controller;

import com.example.artconnect.Entity.Comment;
import com.example.artconnect.service.comment.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    private final NativeWebRequest request;

    public CommentController(CommentService commentService, NativeWebRequest request) {
        this.commentService = commentService;
        this.request = request;
    }

    @PostMapping("/artworks/{artworkId}/comments")
    public ResponseEntity<?> addComment(
            @PathVariable Long artworkId,
            @RequestParam("content") String content,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Comment savedComment = commentService.addCommentToArtwork(artworkId, content, image, username);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
        } catch (IllegalArgumentException | IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/artworks/{artworkId}/comments")
    public ResponseEntity<List<Comment>> getCommentsByArtwork(@PathVariable Long artworkId) {
        List<Comment> comments = commentService.getCommentsByArtwork(artworkId);
        if (comments != null) {
            return new ResponseEntity<>(comments, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
