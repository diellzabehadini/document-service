package com.example.documentservice.controller;

import com.example.documentservice.dto.CreateDocumentRequest;
import com.example.documentservice.model.Document;
import com.example.documentservice.repository.DocumentRepository;
import com.example.documentservice.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = { "http://localhost:8081", "http://localhost:8082" })
@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService documentService;
    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentController(DocumentService documentService, DocumentRepository documentRepository) {
        this.documentService = documentService;
        this.documentRepository = documentRepository;
    }
    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateDocumentRequest request) {
        try {
            Document doc = documentService.createDocument(
                    request.getCourseId(),
                    request.getContent(),
                    request.getUserId(),
                    request.getRole()
            );
            return ResponseEntity.ok(doc);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
        }
    }



    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocumentById(@PathVariable int id) {
        Document doc = documentService.getDocument(id);
        if (doc != null) {
            return ResponseEntity.ok(doc);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Document>> getDocumentsByCourseId(@PathVariable Long courseId) {
        List<Document> docs = documentService.getDocumentsByCourseId(courseId);
        return ResponseEntity.ok(docs);
    }



    @GetMapping
    public Collection<Document> getAll() {
        return documentService.getAllDocuments();
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateDocument(@PathVariable int id,
                                            @RequestBody Document updatedDocument) {
        try {
            Document existing = documentRepository.findById(id).orElse(null);
            if (existing == null) {
                return ResponseEntity.notFound().build();
            }

            existing.setTitle(updatedDocument.getTitle());
            existing.setContent(updatedDocument.getContent());

            Document saved = documentRepository.save(existing);
            return ResponseEntity.ok(saved);

        } catch (ObjectOptimisticLockingFailureException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Document was updated by someone else. Please reload and try again.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while updating document: " + e.getMessage());
        }
    }
}
