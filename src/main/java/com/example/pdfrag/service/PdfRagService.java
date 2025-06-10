// PdfRagService.java
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

@Service
@RequiredArgsConstructor
public class PdfRagService {

    private final ChatClient chatClient;
    private final EmbeddingClient embeddingClient;
    private final VectorStore vectorStore;
    
    public PdfRagService(ChatClient chatClient,
            EmbeddingClient embeddingClient,
            VectorStore vectorStore) {
this.chatClient = chatClient;
this.embeddingClient = embeddingClient;
this.vectorStore = vectorStore;
}

    public String processPdf(MultipartFile file) {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            String text = new PDFTextStripper().getText(document);
            vectorStore.add(List.of(new Document(text)));
            System.err.println("pdf "+text);
            System.err.println("vector "+vectorStore+"\n");
            return "PDF processed successfully!";
        } catch (IOException e) {
            return "Failed to process PDF: " + e.getMessage();
        }
    }


    public String answerQuestion(String question) {
        List<Document> results = vectorStore.similaritySearch(
            SearchRequest.query(question).withTopK(1)
        );
        String context = results.isEmpty() ? "" : results.get(0).getContent();
        return chatClient.call(
            "Answer based on this context:\n" + context + "\nQuestion: " + question
        );
    }
}
