package com.example.todo.application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.todo.application.service.GistService;
import com.example.todo.application.view.GistView;

@RestController
@RequestMapping("/api/export")
public class GistController {
    private final GistService gistService;

    public GistController(GistService gistService) {
        this.gistService = gistService;
    }

    @GetMapping("/{projectId}")
    public GistView exportProjectSummary(@PathVariable Long projectId) {
        return gistService.generateMarkdownContent(projectId);

    }

}