package com.example.artconnect.Entity;

import com.example.artconnect.Entity.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "artworkId"
)
public class Artwork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artworkid")
    private Long artworkId;

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "id")
    private User user;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false,name = "uploaddate")
    @CreationTimestamp
    private LocalDateTime uploadDate ;

    @Column(nullable = false, length = 255)
    private String mediaURL;

    @OneToMany(mappedBy = "artwork", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<CollectionArtwork> collections;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Artwork)) return false;
        Artwork artwork = (Artwork) o;
        return Objects.equals(artworkId, artwork.artworkId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artworkId);
    }

    public Long getArtworkId() {
        return artworkId;
    }

    public void setArtworkId(Long artworkId) {
        this.artworkId = artworkId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getMediaURL() {
        return mediaURL;
    }

    public void setMediaURL(String mediaURL) {
        this.mediaURL = mediaURL;
    }

    public Set<CollectionArtwork> getCollections() {
        return collections;
    }

    public void setCollections(Set<CollectionArtwork> collections) {
        this.collections = collections;
    }
}
