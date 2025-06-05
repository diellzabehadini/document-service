package com.example.documentservice.service;

import com.example.documentservice.model.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class DocumentService {
    private final Map<Integer, Document> documents = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1); // Thread-safe ID generator
    private final RestTemplate restTemplate = new RestTemplate();

    public boolean validateUserAccessToCourse(String courseId, Long userId, String role) {
        String url = "http://localhost:8082/courses/" + courseId + "/validate-access?userId=" + userId + "&role=" + role;
        try {
            ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);
            return response.getBody() != null && response.getBody();
        } catch (Exception e) {
            return false; // treat failure as no access
        }
    }
    public Document createDocument(Long courseId, String content, Long userId, String role) {
        if (!validateUserAccessToCourse(String.valueOf(courseId), userId, role)) {
            throw new RuntimeException("Access Denied");
        }

        int id = idGenerator.getAndIncrement();
        Document doc = new Document();
        doc.setId(id);
        doc.setCourseId(courseId);
        doc.setContent(content);
        doc.setVersion(0);
        documents.put(id, doc);
        return doc;
    }



    public Document getDocument(int id) {
        return documents.get(id);
    }

    public void updateDocument(int id, String newContent) {
        Document doc = documents.get(id);
        if (doc != null) {
            doc.setContent(newContent);
        }
    }
    public List<Document> getDocumentsByCourseId(Long courseId) {
        List<Document> courseDocs = new ArrayList<>();
        for (Document doc : documents.values()) {
            if (Objects.equals(doc.getCourseId(), courseId)) {
                courseDocs.add(doc);
            }
        }
        return courseDocs;
    }

    public boolean updateDocument(int id, String newContent, int clientVersion) {
        Document doc = documents.get(id);
        if (doc != null) {
            synchronized (doc) {
                if (doc.getVersion() == clientVersion) {
                    doc.setContent(newContent);
                    doc.setVersion(doc.getVersion() + 1);
                    return true; // update successful
                } else {
                    return false; // version conflict
                }
            }
        }
        return false; // document not found
    }


    public Collection<Document> getAllDocuments() {
        return documents.values();
    }
}
