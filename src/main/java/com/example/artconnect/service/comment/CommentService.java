package com.example.artconnect.service.comment;

import com.example.artconnect.Entity.Comment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface CommentService {
    Comment addCommentToArtwork(Long artworkId, String content, MultipartFile image, String username) throws IOException ;

    List<Comment> getCommentsByArtwork(Long artworkId);
}
