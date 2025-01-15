package com.nanqing.todoapp.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;

@Service
@Slf4j
public class ImageService {

    private static final String CACHE_DIR = System.getenv("CACHE_DIR");

    private static final String FILENAME = "cached.jpg";

    private static final String CACHE_PATH = CACHE_DIR + File.separator + FILENAME;

    private Instant lastUpdateTime = Instant.MIN;

    private static final File cachedImage;

    static {
        cachedImage = new File(CACHE_PATH);
        log.info("Image absolute path {}", cachedImage.getAbsolutePath());
    }

    public String getCachedImage() throws IOException {

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
