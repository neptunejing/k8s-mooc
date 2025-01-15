package com.nanqing.todoapp.controller;

import com.nanqing.todoapp.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/todo")
public class RequestController {

    @Autowired
    private ImageService imageService;

    private final RestTemplate restTemplate = new RestTemplate();

    // hardcoded backend URL
    private final String backendURL = "http://localhost:8081/todo-api";

    @GetMapping("")
    public String getTodoPage(Model model) throws IOException {
        String imageUrl = imageService.getCachedImage();
        model.addAttribute("imageUrl", imageUrl);

        List<String> todos = null;
        try {
            ResponseEntity<String[]> responseEntity = restTemplate.getForEntity(backendURL, String[].class);
            todos = (responseEntity.getBody() != null)
                    ? Arrays.asList(responseEntity.getBody())
                    : null;
        } catch (Exception e) {
            log.error("Get todo list failed. ", e);
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
