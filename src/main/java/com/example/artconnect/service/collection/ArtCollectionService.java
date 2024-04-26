package com.example.artconnect.service.collection;

import com.example.artconnect.Entity.ArtCollection;
import com.example.artconnect.Entity.Artwork;

import java.util.List;
import java.util.Optional;

public interface ArtCollectionService {

    ArtCollection createCollection(String name,String description,String username);
    List<Artwork> getArtworksInCollection(Long collectionId);
    List<ArtCollection> getLatestCollections(int count);
    List<ArtCollection> getUserCollections(Long userId);

    boolean addArtworkToCollection(Long collectionId, Long artworkId);
    Optional<ArtCollection> getCollectionById(Long collectionId);
    boolean deleteCollection(Long collectionId);

    boolean updateCollection(Long collectionId, Optional<String> name, Optional<String> description);
}
