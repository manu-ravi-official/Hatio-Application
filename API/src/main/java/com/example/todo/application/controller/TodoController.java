package com.example.todo.application.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.application.form.TaskAddForm;
import com.example.todo.application.model.Todo;
import com.example.todo.application.service.TodoService;
import com.example.todo.application.view.TodoView;

@RestController
@RequestMapping("/task")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/{projectId}")
    public TodoView createTask(@PathVariable Long projectId, @RequestBody TaskAddForm form) {
        Todo todo = todoService.createTask(projectId, form);
        return new TodoView(todo);
    }

    @PutMapping("/{taskId}")
    public TodoView editTask(@PathVariable Long taskId, @RequestBody TaskAddForm form) {
        Todo todo = todoService.editTask(taskId, form);
        return new TodoView(todo);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId) {
        return todoService.deleteTask(taskId);
    }

    @PostMapping("/toggle/{id}")
    public ResponseEntity<String> toggleTodoStatus(@PathVariable Long id) {
        return todoService.toggleTodoStatus(id);
    }

}
