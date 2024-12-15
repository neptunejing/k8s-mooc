package com.nanqing.pingpongapp.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/pingpong")
public class RequestController {
    static final AtomicInteger counter = new AtomicInteger(0);

    private static final String DIRECTORY_PATH;

    static {
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            DIRECTORY_PATH = "../files";
        } else {
            DIRECTORY_PATH = "/app/files";
        }
    }

    private static final String FILE_PATH = DIRECTORY_PATH + "/count.txt";

    @PostConstruct
    public void initializeFile() {
        try {
            Path directory = Paths.get(DIRECTORY_PATH);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
            Path file = Paths.get(FILE_PATH);
            if (!Files.exists(file)) {
                Files.createFile(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @GetMapping("/count")
    public String count() {
        int currentCount = counter.incrementAndGet();

        // 覆盖写入文件
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(String.valueOf(currentCount));
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to file: " + FILE_PATH, e);
        }

        return "Counter: " + currentCount;
    }
}
