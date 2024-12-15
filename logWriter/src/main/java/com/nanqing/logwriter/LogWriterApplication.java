package com.nanqing.logwriter;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

@SpringBootApplication
@EnableScheduling
public class LogWriterApplication {

    private static final String DIRECTORY_PATH;

    static {
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            DIRECTORY_PATH = "../files";
        } else {
            DIRECTORY_PATH = "/app/files";
        }
    }

    private static final String FILE_PATH = DIRECTORY_PATH + "/timestamp.txt";

    public static void main(String[] args) {
        SpringApplication.run(LogWriterApplication.class, args);
    }

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

    @Scheduled(fixedRate = 5000)
    public void writeTimestamps() {
        String timestamp = Instant.now().toString();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(timestamp);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
