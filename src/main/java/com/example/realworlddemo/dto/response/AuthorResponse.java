package com.example.realworlddemo.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthorResponse {
    private String username;
    private String bio;
    private String image;
    private boolean following;
}