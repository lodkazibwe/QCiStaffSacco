package com.qualitychemicals.qciss.firebase.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.ExecutionException;

//@CrossOrigin(origins = {"https://qcstaffsacco.com", "https://qcstaffsacco.com/admin"}, allowedHeaders = "*")
@CrossOrigin
@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired ChatService chatService;

    @PostMapping("/sendMessage")
    public ResponseEntity<Message> sendMessage(@Valid @RequestBody MessageDto messageDto) throws ExecutionException, InterruptedException {
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
