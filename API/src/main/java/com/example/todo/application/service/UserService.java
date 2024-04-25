package com.example.todo.application.service;

import com.example.todo.application.form.UserForm;
import com.example.todo.application.model.User;

public interface UserService {
    User signUp(UserForm userForm);

    String login(String email, String password);
}
