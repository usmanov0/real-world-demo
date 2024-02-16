package com.example.realworlddemo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class Profile {
    private final String email;
    private final String username;
    private final String image;
    private final String bio;
}
