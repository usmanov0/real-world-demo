package com.example.realworlddemo.service;

import com.example.realworlddemo.exceptions.UserNotFoundException;
import com.example.realworlddemo.exceptions.UserPasswordIncorrectException;
import com.example.realworlddemo.exceptions.UsernameConflictException;
import com.example.realworlddemo.models.Users;
import com.example.realworlddemo.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Service
public class UserService {
    private final UserRepository userRepo;
    private final BCryptPasswordEncoder bCryptEncoder;


    @Bean
    public static BCryptPasswordEncoder bCryptEncoder() {
        return new BCryptPasswordEncoder();
    }

    public UserService(UserRepository userRepo, BCryptPasswordEncoder bCryptEncoder) {
        this.userRepo = userRepo;
        this.bCryptEncoder = bCryptEncoder;
    }

    public Users registerNewUser(String email, String userName, String password) {
        Users user = userRepo.findUserByUsername(userName);
        if (user != null) {
            throw new UsernameConflictException();
        }

        Users newUser = userRepo.save(Users.builder()
                .username(userName)
                .email(email)
                .password(bCryptEncoder.encode(password))
                .build());

        return userRepo.save(newUser);
    }

    public Users verifyUser(String email, String password) {
        Users user = userRepo.findUserByEmail(email);
        if (user == null) {
            throw new UserNotFoundException();
        }

        if (!bCryptEncoder.matches(password, user.getPassword())) {
            throw new UserPasswordIncorrectException();
        }
        return user;
    }

    public Users updateUser(Users userEntity){
        if(userEntity.getPassword() != null){
            userEntity.setPassword(bCryptEncoder.encode(userEntity.getPassword()));
        }
        return userRepo.save(userEntity);
    }

    public Users findByUsername(String username) {
        Users user = userRepo.findUserByUsername(username);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }

    public Users followUser(String userName, Long loggedInUserId){
        Users userToFollow = findByUsername(userName);
        userRepo.followUser(loggedInUserId, userToFollow.getId());
        return userToFollow;
    }

    public Users unfollowUser(String userName, Long loggedInUserUserId){
        Users userToUnFollow = findByUsername(userName);
        userRepo.unFollowUser(loggedInUserUserId, userToUnFollow.getId());
        return userToUnFollow;
    }
}

