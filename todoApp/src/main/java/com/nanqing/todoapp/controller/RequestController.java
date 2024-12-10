package com.nanqing.todoapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestController {
    @GetMapping("/port")
    public String getPort() {
        return System.getenv("PORT") != null ? System.getenv("PORT") : "Port not set";
    }
}
