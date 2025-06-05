package com.example.documentservice.controller;

import com.example.documentservice.dto.EditMessage;
import com.example.documentservice.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/edit")
    public void handleEdit(EditMessage editMessage) {
        documentService.updateDocument(editMessage.getDocumentId(), editMessage.getContent());
        messagingTemplate.convertAndSend(
                "/topic/document." + editMessage.getDocumentId(),
                editMessage
        );
    }
}
