package com.example.realworlddemo.converter;

import com.example.realworlddemo.dto.response.AuthorResponse;
import com.example.realworlddemo.dto.response.Comment;
import com.example.realworlddemo.dto.response.CommentResponse;
import com.example.realworlddemo.models.Comments;
import com.example.realworlddemo.models.Users;

import java.util.List;
import java.util.stream.Collectors;

public class CommentObjectConverter {

    public CommentResponse entityToResponse(Comments commentEntity){

        Comment response = convert(commentEntity);
        return new CommentResponse(response);
    }

    public List<Comment> entityToResponse(List<Comments> commentsEntity){
        return commentsEntity.stream().map(CommentObjectConverter::convert).collect(Collectors.toList());
    }

    private static Comment convert(Comments commentEntity){
        Users author = commentEntity.getAuthor();
        return Comment.builder()
                .id(commentEntity.getId())
                .body(commentEntity.getBody())
                .createdAt(commentEntity.getCreatedAt())
                .updatedAt(commentEntity.getUpdatedAt())
                .author(AuthorResponse.builder()
                        .username(author.getUsername())
                        .bio(author.getBio())
                        .image(author.getImage())
                        .following(false)
                        .build())
                .build();
    }
}
