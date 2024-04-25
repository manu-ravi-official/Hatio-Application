package com.example.todo.application.form;
import com.example.todo.application.form.validation.Email;
import com.example.todo.application.form.validation.Password;
import com.example.todo.application.form.validation.Username;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserForm {
    @Username
    private String username;
    @Email
    private String email;
    @Password
    private String password;
}
