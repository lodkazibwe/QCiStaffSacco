package com.qualitychemicals.qciss.firebase.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RequestMapping("/chat")
@RestController
public class ChatController {
    @Autowired ChatService chatService;

    @PostMapping("/sendMessage")
    public ResponseEntity<Message> sendMessage(@Valid @RequestBody MessageDto messageDto){
        return new ResponseEntity<>(chatService.sendMessage(messageDto), HttpStatus.OK);
    }
    @PostMapping("/admin/sendMessage/{userName}")
    public ResponseEntity<Message> sendMessage(@Valid @RequestBody MessageDto messageDto, @PathVariable String userName){
        return new ResponseEntity<>(chatService.sendMessage(messageDto, userName), HttpStatus.OK);
    }

    @PutMapping("/readMessages")
    public ResponseEntity<String> readMessage() throws ExecutionException, InterruptedException {
        chatService.readMessages();
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    @PutMapping("/admin/readMessages/{userName}")
    public ResponseEntity<String> readMessageAdmin(@PathVariable String userName) throws ExecutionException, InterruptedException {
        chatService.readMessagesAdmin(userName);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }


}
