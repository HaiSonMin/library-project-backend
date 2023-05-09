package com.haison.libraryapplication.service.impl;

import com.haison.libraryapplication.repository.MessageRepository;
import com.haison.libraryapplication.dto.MessageDTO;
import com.haison.libraryapplication.requestModels.UserQuestionRequest;
import com.haison.libraryapplication.entity.Message;
import com.haison.libraryapplication.requestModels.AdminQuestionRequest;
import com.haison.libraryapplication.service.MessageService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final ModelMapper modelMapper;
    @Override
    public void postMessage(UserQuestionRequest messageUser, String userEmail) {

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setTitle(messageUser.getTitle());
        messageDTO.setQuestion(messageUser.getQuestion());
        messageDTO.setUserEmail(userEmail);

        Message newMessage = this.modelMapper.map(messageDTO,Message.class);

        this.messageRepository.save(newMessage);
    }

    @Override
    public void putMessage(AdminQuestionRequest adminQuestionRequest, String userEmail) throws Exception {
        Optional<Message> message = this.messageRepository.findById(adminQuestionRequest.getId());

        if(message.isEmpty()) {
            throw new Exception("Message not found!!!");
        }

        MessageDTO messageDTO = this.modelMapper.map(message.get(), MessageDTO.class);

        messageDTO.setClosed(true);
        messageDTO.setAdminEmail(userEmail);
        messageDTO.setResponse(adminQuestionRequest.getResponse());

        Message newMassageUpdate = this.modelMapper.map(messageDTO, Message.class);

        this.messageRepository.save(newMassageUpdate);
    }
}
