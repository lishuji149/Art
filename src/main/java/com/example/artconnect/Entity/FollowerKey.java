package com.example.artconnect.Entity;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

@Getter
@EqualsAndHashCode
@Embeddable
public class FollowerKey implements Serializable {
    private Long followerUserId;
    private Long followingUserId;


    public FollowerKey() {}


    public FollowerKey(Long followerUserId, Long followingUserId) {
        this.followerUserId = followerUserId;
        this.followingUserId = followingUserId;
    }

}