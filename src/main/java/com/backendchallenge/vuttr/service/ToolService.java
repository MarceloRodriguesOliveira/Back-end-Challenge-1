package com.backendchallenge.vuttr.service;


import com.backendchallenge.vuttr.entity.Tool;
import com.backendchallenge.vuttr.repository.ToolRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ToolService {
    private final ToolRepository toolRepository;

    public List<Tool> findAll(){
        return toolRepository.findAll();
    }

    public Tool save(Tool tool){
        return toolRepository.save(tool);
    }

    public void deleteById(String toolId){
        var toolExists = toolRepository.existsById(toolId);

        if (toolExists){
            toolRepository.deleteById(toolId);
        }
    }

    public List<Tool> findByTags(String tag){
        return toolRepository.findByTagsContaining(tag);
    }
}
