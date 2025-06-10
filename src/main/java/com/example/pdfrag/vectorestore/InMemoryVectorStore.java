package com.example.pdfrag.vectorestore;


import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class InMemoryVectorStore implements VectorStore {
	
	private final List<Document> storage = new ArrayList<>();

    @Override
    public void add(List<Document> documents) {
        storage.addAll(documents);
    }

    @Override
    public List<Document> similaritySearch(SearchRequest searchRequest) {
        int topK = searchRequest.getTopK() > 0 ? searchRequest.getTopK() : storage.size();
        return storage.subList(0, Math.min(topK, storage.size()));
    }


	@Override
	public Optional<Boolean> delete(List<String> idList) {
		// TODO Auto-generated method stub
		return null;
	}

}
