package com.example.todo.application.form;
import com.example.todo.application.form.validation.Title;

import lombok.Data;

@Data
public class ProjectAddForm {
    @Title
    private String title;
    private String description;
}
