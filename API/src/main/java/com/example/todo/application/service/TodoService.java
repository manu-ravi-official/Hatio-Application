package com.example.todo.application.service;

import org.springframework.http.ResponseEntity;

import com.example.todo.application.form.TaskAddForm;
import com.example.todo.application.model.Todo;

public interface TodoService {

    Todo createTask(Long projectId, TaskAddForm form);

    Todo editTask(Long taskId, TaskAddForm form);

    ResponseEntity<String> deleteTask(Long taskId);

    ResponseEntity<String> toggleTodoStatus(Long id);

}
