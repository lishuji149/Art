package com.example.artconnect.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "Collection_Artwork")
public class CollectionArtwork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "collectionartworkid")
    private Long collectionArtworkId;


    @ManyToOne
    @JoinColumn(name = "collectionid")
    @JsonBackReference
    private ArtCollection artCollection;

    @ManyToOne
    @JoinColumn(name = "artworkid")
    private Artwork artwork;

    @Column(nullable = false,name = "addeddate")
    @CreationTimestamp
    private LocalDateTime addedDate ;

    public Long getCollectionArtworkId() {
        return collectionArtworkId;
    }

    public void setCollectionArtworkId(Long collectionArtworkId) {
        this.collectionArtworkId = collectionArtworkId;
    }

    public ArtCollection getArtCollection() {
        return artCollection;
    }

    public void setArtCollection(ArtCollection artCollection) {
        this.artCollection = artCollection;
    }

    public Artwork getArtwork() {
        return artwork;
    }

    public void setArtwork(Artwork artwork) {
        this.artwork = artwork;
    }

    public LocalDateTime getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(LocalDateTime addedDate) {
        this.addedDate = addedDate;
    }

}
