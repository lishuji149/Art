package com.example.artconnect.Entity;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentid")
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "artworkid", referencedColumnName = "artworkid")
    private Artwork artwork;

    @Column(name = "imageurl")
    private String imageUrl;

    @Column(nullable = false, columnDefinition = "TEXT",name="commenttext")
    private String commentText;

    @Column(nullable = false,name = "commentdate")
    @CreationTimestamp
    private LocalDateTime commentDate ;


}
