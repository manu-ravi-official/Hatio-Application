package com.example.todo.application.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.todo.application.form.ProjectAddForm;
import com.example.todo.application.model.Project;
import com.example.todo.application.model.Todo;
import com.example.todo.application.repository.ProjectRepository;
import com.example.todo.application.repository.TodoRepository;
import com.example.todo.application.service.ProjectService;
import com.example.todo.application.view.ProjectDetailView;
import com.example.todo.application.view.ProjectView;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final TodoRepository todoRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, TodoRepository todoRepository) {
        this.projectRepository = projectRepository;
        this.todoRepository = todoRepository;
    }

    @Override
    public Project createProject(ProjectAddForm form) {
        Project project = new Project();
        project.setTitle(form.getTitle());
        project.setCreatedDate(new Date());
        project.setStatus(Project.Status.ACTIVE);

        return projectRepository.save(project);
    }

    @Override
    public List<ProjectView> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(ProjectView::new)
                .collect(Collectors.toList());
    }

    @Override
    public Project editProject(Long projectId, ProjectAddForm form) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + projectId));
        project.setTitle(form.getTitle());
        return projectRepository.save(project);
    }

    @Override
    public ResponseEntity<String> deleteProject(Long projectId) {
        // Fetch the project by ID from the repository
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + projectId));

        // Delete associated todos if any
        List<Todo> todos = todoRepository.findByProjectId(projectId);
        if (!todos.isEmpty()) {
            todoRepository.deleteAll(todos);
        }

        // Delete the project
        projectRepository.delete(project);

        return ResponseEntity.ok("Project and associated todos deleted successfully");
    }

    @Override
    public ProjectDetailView viewProject(Long projectId) {
        // Retrieve the project from the database based on the projectId
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + projectId));

        // Map the Project entity to ProjectDetailView
        return new ProjectDetailView(project);
    }

}
