package com.example.todo.application.view;

import com.example.todo.application.model.Todo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class TodoView {
    private Long id;
    private String title;

    public TodoView(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
    }
}
