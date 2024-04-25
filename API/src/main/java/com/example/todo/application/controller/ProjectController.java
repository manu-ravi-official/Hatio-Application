package com.example.todo.application.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.application.form.ProjectAddForm;
import com.example.todo.application.model.Project;
import com.example.todo.application.service.ProjectService;
import com.example.todo.application.view.ProjectDetailView;
import com.example.todo.application.view.ProjectView;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ProjectView createProject(@Valid @RequestBody ProjectAddForm form) {
        Project project = projectService.createProject(form);
        return new ProjectView(project);
    }

    @GetMapping
    public List<ProjectView> getAllProjects() {
        return projectService.getAllProjects();
    }

    @PutMapping("/{projectId}")
    public ProjectView editProject(@PathVariable Long projectId, @RequestBody ProjectAddForm form) {
        Project project = projectService.editProject(projectId, form);
        return new ProjectView(project);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<String> deleteProject(@PathVariable Long projectId) {
        return projectService.deleteProject(projectId);
    }

    @GetMapping("/{projectId}")
    public ProjectDetailView viewProject(@PathVariable Long projectId) {
        return projectService.viewProject(projectId);
    }

}