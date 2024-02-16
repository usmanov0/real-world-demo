package com.example.realworlddemo.dto.response;

import com.example.realworlddemo.models.Users;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponse {

    @JsonProperty("user")
    private final User user;

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class User {
        @JsonProperty("username")
        private final String username;

        @JsonProperty("email")
        private final String email;

        @JsonProperty("image")
        private final String image;

        @JsonProperty("bio")
        private final String bio;

        @JsonProperty("token")
        private final String token;
    }

    public static UserResponse fromUserEntity(Users userEntity, String token){
        return new UserResponse(
                new User(
                        userEntity.getUsername(),
                        userEntity.getEmail(),
                        userEntity.getImage(),
                        userEntity.getBio(),
                        token
                ));
    }
}
