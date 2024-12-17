package com.nanqing.todoapp.service;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;

@Service
public class ImageService {

    private static final String CACHE_DIR = System.getenv("CACHE_DIR");

    private static final String FILENAME = "cached.jpg";

    private static final String CACHE_PATH = CACHE_DIR + File.separator + FILENAME;

    private Instant lastUpdateTime = Instant.MIN;

    public String getCachedImage() throws IOException {
        System.out.println("CACHE_PATH: " + CACHE_PATH);
        File cachedImage = new File(CACHE_PATH);

        System.out.println("Image exists: " + cachedImage.exists());
        System.out.println("Image can be read: " + cachedImage.canRead());
        System.out.println("Image absolute path: " + cachedImage.getAbsolutePath());

        if (!cachedImage.exists() || isCacheExpired()) {
            downloadImage();
            lastUpdateTime = Instant.now();
        }
        return FILENAME;
    }

    private void downloadImage() throws IOException {
        URL imageURL = new URL("https://picsum.photos/1200");
        FileUtils.copyURLToFile(imageURL, new File(CACHE_PATH));
    }

    private boolean isCacheExpired() {
        return Instant.now().isAfter(lastUpdateTime.plusSeconds(3600));
    }
}
