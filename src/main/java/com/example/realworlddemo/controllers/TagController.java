package com.example.realworlddemo.controllers;

import com.example.realworlddemo.models.Tags;
import com.example.realworlddemo.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService){
        this.tagService = tagService;
    }

    @GetMapping("/tags")
    ResponseEntity<List<Tags>> getTags(){
        return ResponseEntity.ok(tagService.getAllTags());
    }
}
