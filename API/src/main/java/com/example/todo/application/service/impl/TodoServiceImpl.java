package com.example.todo.application.service.impl;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.todo.application.form.TaskAddForm;
import com.example.todo.application.model.Project;
import com.example.todo.application.model.Todo;
import com.example.todo.application.repository.ProjectRepository;
import com.example.todo.application.repository.TodoRepository;
import com.example.todo.application.service.TodoService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TodoServiceImpl implements TodoService {

    private final ProjectRepository projectRepository;

    private final TodoRepository todoRepository;

    public TodoServiceImpl(ProjectRepository projectRepository, TodoRepository todoRepository) {
        this.projectRepository = projectRepository;
        this.todoRepository = todoRepository;
    }

    @Override
    public Todo createTask(Long projectId, TaskAddForm form) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with id: " + projectId));
        Todo todo = new Todo();
        todo.setTitle(form.getTitle());
        todo.setCreatedDate(new Date());
        todo.setStatus(Todo.Status.PENDING);

        todo.setProject(project);

        return todoRepository.save(todo);
    }

    @Override
    public Todo editTask(Long taskId, TaskAddForm form) {

        Todo todo = todoRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));
        todo.setTitle(form.getTitle());
        return todoRepository.save(todo);
    }

    @Override
    public ResponseEntity<String> deleteTask(Long taskId) {
        Todo todo = todoRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));

        if (todo != null) {
            todoRepository.delete(todo);
            return ResponseEntity.ok("Task deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @Override
    public ResponseEntity<String> toggleTodoStatus(Long id) {
        try {
            Todo todo = todoRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));

            // Toggle the status
            todo.setStatus(todo.getStatus() == Todo.Status.PENDING ? Todo.Status.COMPLETED : Todo.Status.PENDING);

            todoRepository.save(todo);

            return ResponseEntity.ok("Status changed successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Return error response if any exception occurs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
