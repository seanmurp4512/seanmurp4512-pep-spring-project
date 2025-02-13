package com.example.service;

import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.stereotype.Service;
import com.example.entity.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class MessageService {
    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createMessage(Message message){
        
        if (message.getMessageText() == null || message.getMessageText().trim().isEmpty()) {
            throw new IllegalArgumentException("Message text cannot be empty.");
        }
        if (message.getMessageText().length() > 255) {
            throw new IllegalArgumentException("Message text cannot exceed 255 characters.");
        } 
        if (message.getPostedBy() == null || !accountRepository.existsById(message.getPostedBy())) {
            throw new IllegalArgumentException("Invalid user. The postedBy user must exist.");
        }
        return messageRepository.save(message);
    }
    public List<Message> getAllMessages(){
        List<Message> messages = messageRepository.findAll();
        return messages;
    }

    public Message getMessageById(int id){
        Optional<Message> message = messageRepository.findById(id);
        if(message.isEmpty())
            return null;
        return message.get();
    }

    public int deleteMessageById(int id){
        Optional<Message> message = messageRepository.findById(id);
        if(message.isEmpty())
            return 0;
        messageRepository.deleteById(id);
        message = messageRepository.findById(id);
        if(message.isEmpty())
            return 1;
        return 0;
    }

    public int updateMessageById(int id, String text){
        Optional<Message> message = messageRepository.findById(id);
        if (text == null || text.trim().length() < 1) {
            throw new IllegalArgumentException("Message text cannot be empty.");
        }
        if (text.length() > 255) {
            throw new IllegalArgumentException("Message text cannot exceed 255 characters.");
        }
        if(message.isEmpty())
            throw new IllegalArgumentException("No message with this ID.");
        Message mes = message.get();
        mes.setMessageText(text);
        messageRepository.save(mes);
        message = messageRepository.findById(id);
        if(message.get().getMessageText().equals(text))
            return 1;
        else
            return 0;
    }

    public List<Message> getMessagesById(int id){
        List<Message> messages = messageRepository.findByPostedBy(id);
        return messages;
    }
}
