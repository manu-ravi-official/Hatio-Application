package com.example.todo.application.form;
import com.example.todo.application.form.validation.Title;

import lombok.Data;

@Data
public class TaskAddForm {
    @Title
    private String title;
}
