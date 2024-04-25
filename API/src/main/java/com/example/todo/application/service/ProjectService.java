package com.example.todo.application.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.todo.application.form.ProjectAddForm;
import com.example.todo.application.model.Project;
import com.example.todo.application.view.ProjectDetailView;
import com.example.todo.application.view.ProjectView;

public interface ProjectService {
    Project createProject(ProjectAddForm form);

    List<ProjectView> getAllProjects();

    Project editProject(Long projectId, ProjectAddForm form);

    ResponseEntity<String> deleteProject(Long projectId);

    ProjectDetailView viewProject(Long projectId);

}
