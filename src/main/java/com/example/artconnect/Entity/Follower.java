package com.example.artconnect.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@IdClass(FollowerKey.class)
@Table(name="user_followers")
public class Follower {

    @Id
    @Column(name = "follower_user_id")
    private Long followerUserId;

    @Id
    @Column(name = "following_user_id")
    private Long followingUserId;

    @ManyToOne
    @JoinColumn(name = "follower_user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User followerUser;

    @ManyToOne
    @JoinColumn(name = "following_user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User followingUser;


}
