package com.example.artconnect.repository;

import com.example.artconnect.Entity.Follower;
import com.example.artconnect.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowerRepository extends CrudRepository<Follower, Long> {

    void deleteByFollowerUserAndFollowingUser(User follower, User following);

    boolean existsByFollowerUserAndFollowingUser(User follower, User following);
    List<Follower> findAllByFollowerUser(User user);

    List<Follower> findAllByFollowingUser(User user);
}
