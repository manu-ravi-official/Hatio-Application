package com.example.todo.application.view;

import com.example.todo.application.model.Todo;
import com.example.todo.application.model.Todo.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoSummary {

  private Long id;
  private String title;
  private Status status;

  public TodoSummary(Todo todo) {
    this.id = todo.getId();
    this.title = todo.getTitle();
    this.status = todo.getStatus(); // Assuming getter for completed
  }

}
