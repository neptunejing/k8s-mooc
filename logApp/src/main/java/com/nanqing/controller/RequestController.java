package com.nanqing.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.UUID;


@RestController
@RequestMapping("/log")
public class RequestController {
    final static String randomString = UUID.randomUUID().toString();

    @GetMapping("/status")
    public String getCurrentStatus() {
        String timestamp = Instant.now().toString();
        return timestamp + ": " + randomString;
    }
}
