package com.example.documentservice.repository;

import com.example.documentservice.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {
    List<Document> findByCourseId(Long courseId);
}

