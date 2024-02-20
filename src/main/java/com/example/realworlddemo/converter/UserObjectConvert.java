package com.example.realworlddemo.converter;

import com.example.realworlddemo.dto.response.Profile;
import com.example.realworlddemo.dto.response.UserProfileResponse;
import com.example.realworlddemo.dto.response.UserResponse;
import com.example.realworlddemo.models.Users;
import com.example.realworlddemo.service.JWTService;
import org.springframework.stereotype.Service;

@Service
public class UserObjectConvert {
    private final JWTService jwtService;

    public UserObjectConvert(JWTService jwtService){
        this.jwtService = jwtService;
    }

    public UserResponse entityToResponse(Users userEntity){
        return UserResponse.fromUserEntity(userEntity, jwtService.createJwt(userEntity));
    }

    public UserProfileResponse entityToUserProfileResponse(Users userEntity){
        return UserProfileResponse.builder()
                .profile(Profile.builder()
                        .username(userEntity.getUsername())
                        .email(userEntity.getEmail())
                        .image(userEntity.getImage())
                        .bio(userEntity.getBio())
                        .build())
                .build();
    }
}
