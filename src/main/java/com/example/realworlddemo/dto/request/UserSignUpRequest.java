package com.example.realworlddemo.dto.request;

import com.example.realworlddemo.models.Users;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class UserSignUpRequest {
    @JsonProperty("user")
    private User users;

    @Getter
    public class User{

        @JsonProperty("email")
        private String email;

        @JsonProperty("username")
        private String username;

        @JsonProperty("password")
        private String password;
    }
}
