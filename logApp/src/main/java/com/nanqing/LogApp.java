package com.nanqing;

import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class LogApp {
    public static void main(String[] args) {
        String randomString = UUID.randomUUID().toString();

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                String timestamp = Instant.now().toString();
                System.out.println(timestamp + ": " + randomString);
            }
        }, 0, 5000);
    }
}