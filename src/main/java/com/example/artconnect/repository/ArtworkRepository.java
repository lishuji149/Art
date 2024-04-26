package com.example.artconnect.repository;

import com.example.artconnect.Entity.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArtworkRepository extends CrudRepository<Artwork, Long> {
    List<Artwork> findTop10ByOrderByUploadDateDesc();

}
