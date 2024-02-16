package com.example.realworlddemo.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class RegistrationRequest {
    @JsonProperty("user")
    private User user;

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
