package com.example.todo.application.service;

import com.example.todo.application.view.GistView;

public interface GistService {

    String createGist(String string, String markdownContent);

    GistView generateMarkdownContent(Long projectId);

}