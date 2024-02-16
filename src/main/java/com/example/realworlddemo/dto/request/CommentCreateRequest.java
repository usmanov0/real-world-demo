package com.example.realworlddemo.dto.request;

import lombok.Getter;

@Getter
public class CommentCreateRequest {
    private Comment comment;

    @Getter
    public class Comment{
        String body;
    }
}
