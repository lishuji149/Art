package com.example.artconnect.service.follower;

import com.example.artconnect.Entity.User;

import java.util.List;

public interface FollowerService {
    void followUser(User follower, User following);
    void unfollowUser(User follower, User following);

    boolean alreadyFollows(User follower, User following);
    List<User> getFollowings(User user);

    List<User> getFollowers(User user);
}
