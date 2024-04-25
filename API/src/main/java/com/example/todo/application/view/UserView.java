package com.example.todo.application.view;

import com.example.todo.application.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserView {
    private Long id;
    private String username;
    private String email;

    public static UserView fromUser(User user) {
        UserView userView = new UserView();
        userView.setId(user.getId());
        userView.setUsername(user.getUsername());
        userView.setEmail(user.getEmail());
        return userView;
    }
}
