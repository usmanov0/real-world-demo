package com.example.realworlddemo.dto;

import com.example.realworlddemo.models.Users;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.apache.catalina.User;

@Getter
public class UserLoginRequest {
    @JsonProperty("user")
    private User user;

    @Getter
    public class User{

        @JsonProperty("email")
        private String email;

        @JsonProperty("password")
        private String password;
    }
}
