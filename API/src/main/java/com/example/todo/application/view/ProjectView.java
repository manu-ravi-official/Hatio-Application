package com.example.todo.application.view;
import com.example.todo.application.model.Project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectView {
    private Long id;
    private String title;

    public ProjectView(Project project) {
        this.id = project.getId();
        this.title = project.getTitle();
    }
}
