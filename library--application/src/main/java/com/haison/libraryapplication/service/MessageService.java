package com.haison.libraryapplication.service;

import com.haison.libraryapplication.dto.MessageDTO;
import com.haison.libraryapplication.requestModels.UserQuestionRequest;
import com.haison.libraryapplication.requestModels.AdminQuestionRequest;

public interface MessageService {
    public void postMessage(UserQuestionRequest messageUser, String userEmail) ;
    public void putMessage(AdminQuestionRequest adminQuestionRequest, String userEmail) throws Exception ;
}
