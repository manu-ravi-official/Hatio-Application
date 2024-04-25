package com.example.todo.application.view;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GistView {
    private String gistUrl;
    private String fileName;
    private String content;
}
