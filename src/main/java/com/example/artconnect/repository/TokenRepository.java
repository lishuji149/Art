package com.example.artconnect.repository;

import java.util.Optional;

import com.example.artconnect.Entity.Tokens;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface TokenRepository extends CrudRepository<Tokens, Long> {
    Optional<Tokens> findById(Long id);
}