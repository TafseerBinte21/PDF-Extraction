package com.example.pdfrag.service;

import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PdfRagService {

    private final ChatClient chatClient = null;
    private final EmbeddingClient embeddingClient = null;
    private final VectorStore vectorStore = null;

    private String extractedText = "";

    public String processPdf(MultipartFile file) {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            extractedText = stripper.getText(document);
            
            // Create a Document with the extracted text
            Document doc = new Document(extractedText);
            
            // Add to vector store
//            vectorStore.add(List.of(doc));
            vectorStore.add((List<Document>) doc);
            
            
            return "PDF uploaded and embedded successfully.";
        } catch (IOException e) {
            return "Error processing PDF: " + e.getMessage();
        }
    }

    public String answerQuestion(String question) {
        // Embed the question
        List<Double> embedding = embeddingClient.embed(question);
        
        // Perform similarity search
        List<Document> results = vectorStore.similaritySearch(
            SearchRequest.query(question).withTopK(1)
        );

        String context = results.isEmpty() ? "" : results.get(0).getContent();

        String prompt = String.format(
            "Answer the question based on the context below:%n%nContext:%n%s%n%nQuestion: %s",
            context,
            question
        );

        return chatClient.call(prompt);
    }
}