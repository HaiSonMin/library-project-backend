package com.haison.libraryapplication.controller;

import com.haison.libraryapplication.dto.MessageDTO;
import com.haison.libraryapplication.requestModels.UserQuestionRequest;
import com.haison.libraryapplication.requestModels.AdminQuestionRequest;
import com.haison.libraryapplication.service.MessageService;
import com.haison.libraryapplication.utils.ExtractJWT;
import lombok.AllArgsConstructor;
import org.apache.catalina.core.StandardHost;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("https://localhost:3000")
@RestController
@RequestMapping("command/messages/secure")
@AllArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping("post-message")
    public ResponseEntity<String> postMessage(@RequestHeader("Authorization") String token,
                                                  @RequestBody UserQuestionRequest messageUser) {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");

         this.messageService.postMessage(messageUser,userEmail);

        return new ResponseEntity<>("Post successfully", HttpStatus.CREATED);
    }

    @PutMapping("put-message")
    public ResponseEntity<String> putMessage(@RequestHeader("Authorization") String token,
                                             @RequestBody AdminQuestionRequest adminQuestionRequest) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        String userType = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");

        if(userType == null || !userType.equals("admin")) {
            throw new Exception("Only admin has the right to update data!!");
        }

        this.messageService.putMessage(adminQuestionRequest,userEmail);

        return new ResponseEntity<>("Update message successfully", HttpStatus.OK);
    }
}
