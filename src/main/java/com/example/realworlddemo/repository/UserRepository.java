package com.example.realworlddemo.repository;

import com.example.realworlddemo.models.Users;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.nio.file.LinkOption;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Users findUserByUsername(
            @Param("username") String username
    );

    Users findUserByEmail(
            @Param("email") String email
    );

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_followers(user_id, follower_id) VALUES(?1,?2)",nativeQuery = true)
    void followUser(Long loggedInUserUserId, Long userToFollow);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_followers WHERE users_id = ?1 AND followers_id = ?2", nativeQuery = true)
    void unFollowUser(Long loggedInUserUserId, Long userToFollow);
}
