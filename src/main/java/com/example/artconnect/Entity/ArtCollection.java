package com.example.artconnect.Entity;

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
@Table(name = "Collections")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "collectionId"
)
public class ArtCollection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="collectionid")
    private Long collectionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", referencedColumnName = "id")
    private User user;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false,name ="creationdate")
    @CreationTimestamp
    private LocalDateTime creationDate ;


    @OneToMany(mappedBy = "artCollection", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<CollectionArtwork> artworks;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArtCollection)) return false;
        ArtCollection that = (ArtCollection) o;
        return Objects.equals(collectionId, that.collectionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(collectionId);
    }

    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Set<CollectionArtwork> getArtworks() {
        return artworks;
    }

    public void setArtworks(Set<CollectionArtwork> artworks) {
        this.artworks = artworks;
    }

}
