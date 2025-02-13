package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import com.example.service.AccountService;
import com.example.service.MessageService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    private final AccountService accountService;
    private final MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.messageService = messageService;
        this.accountService = accountService;
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Account account) {
        try {
            Account savedAccount = accountService.registerAccount(account);
            return ResponseEntity.ok(savedAccount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Account account){
        try {
            Account savedAccount = accountService.loginAccount(account);
            return ResponseEntity.ok(savedAccount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<?> createMessage(@RequestBody Message message){
        try {
            Message savedMessage = messageService.createMessage(message);
            return ResponseEntity.ok(savedMessage);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/messages")
    public ResponseEntity<?> getAllMessages(){
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<?> getMessageById(@PathVariable int messageId){
            Message savedMessage = messageService.getMessageById(messageId);
            return ResponseEntity.ok(savedMessage);
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<?> deleteMessageById(@PathVariable int messageId){
        int i = messageService.deleteMessageById(messageId);
        if(i==0)
            return null;
        return ResponseEntity.ok(i);
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<?> updateMessageById(@PathVariable int messageId,@RequestBody Map<String, Object> updates ){
        String messageText = updates.get("messageText").toString().trim();
        try {
            int i = messageService.updateMessageById(messageId, messageText);
            return ResponseEntity.ok(i);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<?> getMessagesById(@PathVariable int accountId){
        return ResponseEntity.ok(messageService.getMessagesById(accountId));
    }
}
