package com.example.realworlddemo.service;

import com.example.realworlddemo.models.Tags;
import com.example.realworlddemo.repository.TagRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepo;

    public TagService(TagRepository tagRepo) {
        this.tagRepo = tagRepo;
    }

    public List<Tags> getAllTags(){
        return tagRepo.findAll();
    }

    public List<Tags> createTag(String tags){
        return Collections.singletonList(tagRepo.save(new Tags(tags)));
    }

    public List<Tags> createTags(List<String> tags){
        List<Tags> tagEntity = new ArrayList<>();
        for (String tag : tags){
            if (!tagRepo.findByName(tag).isPresent()){
                this.createTag(tag);
            }
            tagEntity.add(tagRepo.findByName(tag).get());
        }
        return tagEntity;
    }
}
