package com.example.realworlddemo.dto;

import com.example.realworlddemo.models.Comments;
import lombok.Getter;

@Getter
public class CommentCreateReq {
    private Comment comment;

    @Getter
    public class Comment{
        String body;
    }
}
