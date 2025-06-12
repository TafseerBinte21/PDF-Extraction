package com.example.pdfrag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PdfRagSpringAiApplication {
    public static void main(String[] args) {
        SpringApplication.run(PdfRagSpringAiApplication.class, args);
    }
}