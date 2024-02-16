package com.example.realworlddemo.repository;

import com.example.realworlddemo.models.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tags,Long> {
    Optional<Tags> findByName(String name);
}
