package com.example.pdfrag.controller;

import com.example.pdfrag.service.PdfRagService;

import java.io.IOException;
import java.util.Map;

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
    
	@RequestMapping(value = "/ask", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> askQuestion(@RequestBody Map<String, String> requestBody) {
        String question = requestBody.get("question");
        String response = pdfRagService.answerQuestion(question);
        return ResponseEntity.ok(response);
    }

}