package com.nanqing.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;


@RestController
@RequestMapping("/log")
public class RequestController {

    private static final String FILE_PATH;

    static {
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            FILE_PATH = "../files/timestamp.txt";
        } else {
            FILE_PATH = "/app/files/timestamp.txt";
        }
    }

    @GetMapping("/status")
    public String getCurrentStatus() {
        StringBuilder response = new StringBuilder();
        StringBuilder content = new StringBuilder();
        System.out.println("Hit /log/status");

        try {
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
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

    private String calculateMD5Hash(String content) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(content.getBytes());
        return HexFormat.of().formatHex(digest);
    }
}
