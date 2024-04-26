package com.example.artconnect.repository;

import com.example.artconnect.Entity.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findByArtworkArtworkId(Long artworkId);
}
