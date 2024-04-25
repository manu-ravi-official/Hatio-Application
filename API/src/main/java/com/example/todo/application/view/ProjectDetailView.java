package com.example.todo.application.view;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.todo.application.model.Project;
import com.example.todo.application.model.Todo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDetailView {

  private Long id;
  private String title;
  private String description;
  private Date createdDate;
  private List<TodoSummary> todos; // Use TodoSummary instead of Todo

  public ProjectDetailView(Project project) {
    this.id = project.getId();
    this.title = project.getTitle();
    this.createdDate = project.getCreatedDate();

    this.todos = new ArrayList<>();
    for (Todo todo : project.getTodos()) {
      todos.add(new TodoSummary(todo));
    }
  }
}
