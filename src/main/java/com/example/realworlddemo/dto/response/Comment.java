package com.example.realworlddemo.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class Comment {
    private Long id;
    private String body;
    private Date createdAt;
    private Date updatedAt;
    private AuthorResponse author;
}
