package com.example.todo.application.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.todo.application.exception.BadRequestException;
import com.example.todo.application.model.Project;
import com.example.todo.application.model.Todo;
import com.example.todo.application.repository.ProjectRepository;
import com.example.todo.application.service.GistService;
import com.example.todo.application.view.GistView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GistServiceImpl implements GistService {

    @Value("${github.username}")
    private String username;

    @Value("${github.token}")
    private String token;

    private final ProjectRepository projectRepository;

    public GistServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public GistView generateMarkdownContent(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + projectId));
        StringBuilder markdown = new StringBuilder();
        markdown.append("# ").append(project.getTitle()).append("\n");

        List<Todo> todos = project.getTodos();
        if (todos.isEmpty()) {
            markdown.append("## Summary: 0 / 0 completed\n\n");
            markdown.append("### Pending Todos:\n");
            markdown.append("No pending todos\n\n");
            markdown.append("### Completed Todos:\n");
            markdown.append("No completed todos\n");
        } else {
            long completedCount = todos.stream().filter(todo -> todo.getStatus() == Todo.Status.COMPLETED).count();
            markdown.append("## Summary: ").append(completedCount).append(" /").append(todos.size())
                    .append(" completed\n\n");

            markdown.append("### Pending Todos:\n");
            todos.stream().filter(todo -> todo.getStatus() == Todo.Status.PENDING)
                    .forEach(todo -> markdown.append("- [ ] ").append(todo.getTitle()).append("\n"));

            markdown.append("\n### Completed Todos:\n");
            todos.stream().filter(todo -> todo.getStatus() == Todo.Status.COMPLETED)
                    .forEach(todo -> markdown.append("- [x] ").append(todo.getTitle()).append("\n"));
        }

        String gistUrl = createGist(project.getTitle() + ".md", markdown.toString());
        return new GistView(gistUrl, project.getTitle() + ".md", markdown.toString());

    }

    @Override
    public String createGist(String fileName, String content) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(username, token);

        String apiUrl = "https://api.github.com/gists";
        String encodedContent = content.replace("\n", "\\n");
        String payload = "{\"files\": {\"" + fileName + "\": {\"content\": \"" +
                encodedContent + "\"}}}";
        HttpEntity<String> request = new HttpEntity<>(payload, headers);

        RestTemplate restTemplate = new RestTemplate();

        String response = restTemplate.postForObject(apiUrl, request, String.class);
        return extractGistUrl(response);

    }

    private String extractGistUrl(String response) {
        try {
            // Parse the JSON response
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response);

            // Retrieve the "html_url" field from the JSON response
            JsonNode htmlUrlNode = jsonNode.get("html_url");
            if (htmlUrlNode != null && htmlUrlNode.isTextual()) {
                return htmlUrlNode.textValue();
            } else {
                throw new BadRequestException("Gist URL not found in the response");
            }
        } catch (JsonProcessingException e) {
            // Handle JSON parsin exception
            throw new BadRequestException("Error parsing JSON response", e);
        }
    }
}
