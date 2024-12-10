package com.nanqing.pingpongapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/pingpong")
public class RequestController {
    static final AtomicInteger counter = new AtomicInteger(0);

    @GetMapping("/count")
    public String count() {
        String res = "pong " + counter.get();
        counter.incrementAndGet();
        return res;
    }
}
