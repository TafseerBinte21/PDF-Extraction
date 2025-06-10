package com.example.pdfrag.controller;

import com.example.pdfrag.service.PdfRagService;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    @Autowired
    private PdfRagService pdfRagService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPdf(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(pdfRagService.processPdf(file));
    }
    @GetMapping("/ask")
    public ResponseEntity<String> askQuestion(@RequestParam("question") String question) {
        return ResponseEntity.ok(pdfRagService.answerQuestion(question));
    }
}