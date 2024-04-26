package com.example.artconnect.Entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "artwork", referencedColumnName = "artworkId")
    private Artwork artwork;


}
