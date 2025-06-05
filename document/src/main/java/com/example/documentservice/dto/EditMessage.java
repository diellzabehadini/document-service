package com.example.documentservice.dto;

public class EditMessage {
    private int documentId;
    private String content;

    public EditMessage() {}

    public EditMessage(int documentId, String content) {
        this.documentId = documentId;
        this.content = content;
    }


    public int getDocumentId() { return documentId; }
    public void setDocumentId(int documentId) { this.documentId = documentId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
