package com.eku.EKU;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class EKUApplication {
    public static void main(String[] args) {
        SpringApplication.run(EKUApplication.class, args);
    }
}
