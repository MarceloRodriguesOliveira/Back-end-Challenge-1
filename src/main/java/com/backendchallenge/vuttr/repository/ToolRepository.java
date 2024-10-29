package com.backendchallenge.vuttr.repository;

import com.backendchallenge.vuttr.entity.Tool;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ToolRepository  extends MongoRepository<Tool, String> {
    List<Tool> findByTagsContaining(String tag);
}
