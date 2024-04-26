package com.example.artconnect.service.follower;

import com.example.artconnect.Entity.Follower;
import com.example.artconnect.Entity.User;
import com.example.artconnect.repository.FollowerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowerServiceImpl implements FollowerService{

    private final FollowerRepository followerRepository;

    public FollowerServiceImpl(FollowerRepository followerRepository) {
        this.followerRepository = followerRepository;
    }

    @Transactional
    public void followUser(User follower, User following) {
        if (!alreadyFollows(follower, following)) {
            Follower followRelation = new Follower();

            followRelation.setFollowerUserId(follower.getId());
            followRelation.setFollowingUserId(following.getId());


            followRelation.setFollowerUser(follower);
            followRelation.setFollowingUser(following);

            followerRepository.save(followRelation);
        }
    }

    @Transactional
    public void unfollowUser(User follower, User following) {
        followerRepository.deleteByFollowerUserAndFollowingUser(follower, following);
    }

    // 获取用户关注的人
    public List<User> getFollowings(User user) {
        return followerRepository.findAllByFollowerUser(user)
                .stream()
                .map(Follower::getFollowingUser)
                .collect(Collectors.toList());
    }

    // 获取用户的粉丝
    public List<User> getFollowers(User user) {
        return followerRepository.findAllByFollowingUser(user)
                .stream()
                .map(Follower::getFollowerUser)
                .collect(Collectors.toList());
    }
    public boolean alreadyFollows(User follower, User following) {
        return followerRepository.existsByFollowerUserAndFollowingUser(follower, following);
    }
}
