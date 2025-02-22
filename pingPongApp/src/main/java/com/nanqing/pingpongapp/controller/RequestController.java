package com.nanqing.pingpongapp.controller;

import com.nanqing.pingpongapp.repo.CountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

import com.example.pingpong.model.CountEntity;

@Slf4j
@RestController
@RequestMapping("/pingpong")
public class RequestController {
    static final AtomicInteger counter = new AtomicInteger(0);

    @Autowired
    private CountRepository currentCountRepository;

    @GetMapping("/count")
    public String count() {
        int currentCount = counter.incrementAndGet();

        CountEntity currentCountEntity = new CountEntity();
        currentCountEntity.setCount(currentCount);

        try {
            currentCountRepository.save(currentCountEntity);
            log.info("Current count is {}", currentCount);
        } catch (Exception e) {
            log.error("Save count to DB failed", e);
        }

        return "Counter: " + currentCount;
    }
}
