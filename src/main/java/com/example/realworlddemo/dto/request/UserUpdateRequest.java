package com.example.realworlddemo.dto.request;

import com.example.realworlddemo.models.Users;
import lombok.Getter;

@Getter
public class UserUpdateRequest {
    private User users;

    @Getter
    public class User{
        private String username;
        private String email;
        private String password;
        private String bio;
        private String image;
    }
}
