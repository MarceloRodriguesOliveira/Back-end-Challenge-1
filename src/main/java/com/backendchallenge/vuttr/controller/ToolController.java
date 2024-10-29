package com.backendchallenge.vuttr.controller;

import com.backendchallenge.vuttr.entity.Tool;
import com.backendchallenge.vuttr.service.ToolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tools")
public class ToolController {
    private final ToolService toolService;

    @GetMapping
    public ResponseEntity<List<Tool>> findAllTools(){
        return ResponseEntity.ok().body(toolService.findAll());
    }

    @PostMapping
    public ResponseEntity<Tool> insertNewTool(@RequestBody Tool toolDto){
        var newTool = toolService.save(toolDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTool);
    }

    @DeleteMapping("/{toolId}")
    public ResponseEntity<Void> deleteById(@PathVariable("toolId") String toolId){
        toolService.deleteById(toolId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/tag")
    public ResponseEntity<List<Tool>> findByTags(@RequestParam String tag){
        return ResponseEntity.ok().body(toolService.findByTags(tag));
    }


}
