package com.nanqing.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.HexFormat;


@Slf4j
@RestController
@RequestMapping("/log")
public class RequestController {

    private static final String FILE_PREFIX;
    private static final String CONFIG_PREFIX;
    private static final String TIMESTAMP_FILE = "timestamp.txt";
    private static final String PINGPONG_COUNT_FILE = "count.txt";
    private static final String CONFIGMAP_FILE = "ppconfig.txt";

    @Value("${ENV_VAR}")
    private String myEnvVar;

    static {
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            FILE_PREFIX = "../files/";
            CONFIG_PREFIX = "../config/";
        } else {
            FILE_PREFIX = "/app/files/";
            CONFIG_PREFIX = "/app/config/";
        }
    }

    @GetMapping("/status")
    public String getCurrentStatus() {
        String filename = FILE_PREFIX + TIMESTAMP_FILE;
        StringBuilder response = new StringBuilder();
        StringBuilder content = new StringBuilder();

        try {
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                String hash = calculateMD5Hash(content.toString());
                response.append("File Hash (MD5): ").append(hash).append("\n");
                response.append("File Content: ").append(content);
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "Error reading or hashing file: " + e.getMessage();
        }
        return response.toString();
    }

    @GetMapping("/ping-pong-count")
    public String pingPongCount() {
        String configFileName = CONFIG_PREFIX + CONFIGMAP_FILE;
        String filename = FILE_PREFIX + PINGPONG_COUNT_FILE;
        StringBuilder response = new StringBuilder();
        StringBuilder content = new StringBuilder();

        try {
            try (BufferedReader reader = new BufferedReader(new FileReader(configFileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                response.append("file Content: ").append(content).append("<br>");
                response.append("env variable: ").append(myEnvVar).append("<br>");
            }
        } catch (IOException e) {
            log.error("Print configmap volume failed. {}", e.getMessage());
        }

        try {
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                String timestamp = Instant.now().toString();
                String hash = calculateMD5Hash(content.toString());
                response.append(timestamp).append(": ").append(hash).append("<br>");
                response.append("Ping / Pongs: ").append(content);
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            log.error("Print log file failed. {}", e.getMessage());
        }

        return response.toString();
    }

    private String calculateMD5Hash(String content) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(content.getBytes());
        return HexFormat.of().formatHex(digest);
    }
}
