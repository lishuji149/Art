package com.example.artconnect.repository;

import com.example.artconnect.Entity.ArtCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtCollectionRepository extends JpaRepository<ArtCollection, Long> {
    List<ArtCollection> findByUserId(Long userId);
}