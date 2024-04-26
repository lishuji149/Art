package com.example.artconnect.controller;

import com.example.artconnect.Entity.User;
import com.example.artconnect.service.follower.FollowerService;
import com.example.artconnect.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/followers")
public class FollowerController {

    private final FollowerService followerService;
    private final NativeWebRequest request;
    private final UserService userService;

    public FollowerController(FollowerService followerService, NativeWebRequest request, UserService userService) {
        this.followerService = followerService;
        this.request = request;
        this.userService = userService;
    }

    @PostMapping("/follow/{userId}/{followingUserId}")
    public ResponseEntity<?> followUser(@PathVariable Long userId, @PathVariable Long followingUserId) {
        Optional<User> follower = userService.findById(userId);
        Optional<User> following = userService.findById(followingUserId);

        if (!follower.isPresent() || !following.isPresent()) {
            return ResponseEntity.badRequest().body("Invalid user IDs");
        }

        followerService.followUser(follower.get(), following.get());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/unfollow/{userId}/{followingUserId}")
    public ResponseEntity<?> unfollowUser(@PathVariable Long userId, @PathVariable Long followingUserId) {
        Optional<User> follower = userService.findById(userId);
        Optional<User> following = userService.findById(followingUserId);

        if (!follower.isPresent() || !following.isPresent()) {
            return ResponseEntity.badRequest().body("Invalid user IDs");
        }

        followerService.unfollowUser(follower.get(), following.get());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/followings")
    public ResponseEntity<List<User>> getUserFollowings(@PathVariable Long userId) {
        return userService.findById(userId)
                .map(user -> ResponseEntity.ok(followerService.getFollowings(user)))
                .orElseGet(() -> ResponseEntity.badRequest().body(null));
    }

    // 获取用户的粉丝
    @GetMapping("/{userId}/followers")
    public ResponseEntity<List<User>> getUserFollowers(@PathVariable Long userId) {
        return userService.findById(userId)
                .map(user -> ResponseEntity.ok(followerService.getFollowers(user)))
                .orElseGet(() -> ResponseEntity.badRequest().body(null));
    }

}
