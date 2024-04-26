package com.example.artconnect.service.collection;

import com.example.artconnect.Entity.Artwork;
import com.example.artconnect.Entity.ArtCollection;
import com.example.artconnect.Entity.CollectionArtwork;
import com.example.artconnect.Entity.User;
import com.example.artconnect.repository.ArtworkRepository;
import com.example.artconnect.repository.ArtCollectionRepository;
import com.example.artconnect.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArtArtCollectionServiceImpl implements ArtCollectionService {
    private final ArtCollectionRepository artCollectionRepository;
    private final UserRepository userRepository;
    private final ArtworkRepository artworkRepository;

    public ArtArtCollectionServiceImpl(ArtCollectionRepository artCollectionRepository, UserRepository userRepository, ArtworkRepository artworkRepository) {
        this.artCollectionRepository = artCollectionRepository;
        this.userRepository = userRepository;
        this.artworkRepository = artworkRepository;
    }

    public ArtCollection createCollection(String name,String description,String username) {
        User user = userRepository.findByUsername(username).get();
        ArtCollection collection = new ArtCollection();
        collection.setName(name);
        collection.setDescription(description);
        collection.setUser(user);
        return artCollectionRepository.save(collection);
    }

    public List<Artwork> getArtworksInCollection(Long collectionId) {
        Optional<ArtCollection> collectionOpt = artCollectionRepository.findById(collectionId);
        if (collectionOpt.isPresent()) {
            ArtCollection collection = collectionOpt.get();
            return collection.getArtworks().stream()
                    .map(CollectionArtwork::getArtwork)
                    .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("Collection not found");
        }
    }

    public List<ArtCollection> getLatestCollections(int count) {
        Pageable pageable = PageRequest.of(0, count, Sort.by(Sort.Direction.DESC, "creationDate"));
        Page<ArtCollection> page = artCollectionRepository.findAll(pageable);
        return page.getContent();
    }
    public List<ArtCollection> getUserCollections(Long userId) {
        return artCollectionRepository.findByUserId(userId);
    }

    public boolean deleteCollection(Long collectionId){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            return false;
        }
        Optional<ArtCollection> collection = artCollectionRepository.findById(collectionId);
        if (!collection.isPresent()) {
            return false;
        }
        if (!collection.get().getUser().getId().equals(user.get().getId())) {
            return false;
        }
        artCollectionRepository.deleteById(collectionId);
        return true;

    }
    public boolean updateCollection(Long collectionId, Optional<String> name, Optional<String> description) {

        Optional<ArtCollection> collection = artCollectionRepository.findById(collectionId);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent() || !collection.get().getUser().getId().equals(user.get().getId())) {
            return false;
        }
        if (collection.isPresent()) {
            if (name.isPresent()) {
                collection.get().setName(name.get());
            }
            if (description.isPresent()) {
                collection.get().setDescription(description.get());
            }
            artCollectionRepository.save(collection.get());
            return true;
        } else {
            return false;
        }

    }
    public Optional<ArtCollection> getCollectionById(Long collectionId) {
        return artCollectionRepository.findById(collectionId);
    }
    public boolean addArtworkToCollection(Long collectionId, Long artworkId) {
        Optional<ArtCollection> collectionOpt = artCollectionRepository.findById(collectionId);
        Optional<Artwork> artworkOpt = artworkRepository.findById(artworkId);

        if(collectionOpt.isPresent() && artworkOpt.isPresent()) {
            ArtCollection artCollection = collectionOpt.get();
            Artwork artwork = artworkOpt.get();

            CollectionArtwork collectionArtwork = new CollectionArtwork();
            collectionArtwork.setArtCollection(artCollection);
            collectionArtwork.setArtwork(artwork);

            artCollection.getArtworks().add(collectionArtwork);
            artCollectionRepository.save(artCollection);
            return true;
        }

        return false;
    }
}
