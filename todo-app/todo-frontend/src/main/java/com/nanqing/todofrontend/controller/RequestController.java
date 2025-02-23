package com.nanqing.todofrontend.controller;

import com.nanqing.todofrontend.entity.Todo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/todo")
public class RequestController {

    private final RestTemplate restTemplate = new RestTemplate();

    // hardcoded backend URL
    private final String backendURL = "http://localhost:8090/todo-api";

    @GetMapping("")
    public String getTodoPage(Model model) throws IOException {
        List<Todo> todos;
        try {
            ResponseEntity<Todo[]> responseEntity = restTemplate.getForEntity(backendURL, Todo[].class);
            Todo[] todoArray = responseEntity.getBody();
            todos = (todoArray != null) ? Arrays.asList(todoArray) : new ArrayList<>();
        } catch (Exception e) {
            log.error("Get todos failed.", e);
            todos = new ArrayList<>();
        }
        model.addAttribute("todos", todos);
        return "todo";
    }

    @PostMapping("/add")
    public String addTodo(String todoContent) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> requestEntity = new HttpEntity<>(todoContent, headers);
        restTemplate.postForEntity(backendURL, requestEntity, Void.class);

        return "redirect:/todo";
    }
}
