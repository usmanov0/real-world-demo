package com.example.realworlddemo.controllers;

import com.example.realworlddemo.models.Tags;
import com.example.realworlddemo.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService){
        this.tagService = tagService;
    }

    @GetMapping("/")
    ResponseEntity<List<Tags>> getTags(){
        return ResponseEntity.ok(tagService.getAllTags());
    }
}
