package com.example.todo.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.todo.application.model.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByProjectId(Long projectId);

}
