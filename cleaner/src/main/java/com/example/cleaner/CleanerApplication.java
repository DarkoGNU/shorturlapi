package com.example.cleaner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CleanerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CleanerApplication.class, args);
    }

}
