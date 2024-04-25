package com.example.todo.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.todo.application.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

}
