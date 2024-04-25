package com.example.todo.application.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.application.form.LoginForm;
import com.example.todo.application.form.UserForm;
import com.example.todo.application.model.User;
import com.example.todo.application.service.UserService;
import com.example.todo.application.view.LoginView;
import com.example.todo.application.view.UserView;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public UserView signUp(@RequestBody UserForm userForm) {
        User newUser = userService.signUp(userForm);
        return UserView.fromUser(newUser);
    }

    @PostMapping("/login")
    public LoginView login(@RequestBody LoginForm loginForm) {
        String token = userService.login(loginForm.getEmail(), loginForm.getPassword());
        return new LoginView(token);
    }
}
