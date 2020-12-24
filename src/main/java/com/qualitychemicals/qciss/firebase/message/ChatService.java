package com.qualitychemicals.qciss.firebase.message;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;

import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.transaction.dto.SavingsTransactionsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ChatService {
    @Autowired RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(ChatService.class);
    public Message sendMessage(MessageDto messageDto) throws ExecutionException, InterruptedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String sender=auth.getName();
        Date date =new Date();
        Message message=new Message();
        message.setText(messageDto.getText());
        message.setSentBy(sender);
        message.setSentAt(date);
        message.setStatus("RECEIVED");

        Firestore bdFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> future=bdFirestore.collection("chat")
                .document(sender).collection("messages").document()
                .set(message);
        logger.info("Update time : " + future.get().getUpdateTime());
        return message;
    }

    public Message sendMessage(MessageDto messageDto, String userName ){

        Message message=new Message();
        Date date =new Date();
        message.setText(messageDto.getText());
        message.setSentAt(date);
        message.setSentBy("ADMIN");
        message.setStatus("RECEIVED");

        Firestore bdFirestore = FirestoreClient.getFirestore();
        bdFirestore.collection("chat")
                .document(userName).collection("messages").document()
                .set(message);
        return message;
    }

    public SavingsTransactionsDto sendMessage() {
        try {
            return restTemplate.getForObject(
                    "http://localhost:8082/chat/sendMessage", SavingsTransactionsDto.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }

    public void readMessage(ReadDto readDto){
        Firestore bdFirestore = FirestoreClient.getFirestore();
        bdFirestore.collection("chat").document(readDto.getUserName()).collection("messages")
                .document(readDto.getKey()).update(
                "status",Status.READ
        );

    }

    public void readMessages() throws ExecutionException, InterruptedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        Firestore bdFirestore = FirestoreClient.getFirestore();
        WriteBatch writeBatch = bdFirestore.batch();
        List<QueryDocumentSnapshot> documentsInBatch =bdFirestore.collection("chat")
                .document(userName).collection("messages")
                .whereEqualTo("status", "RECEIVED")
                .whereEqualTo("sentBy", "ADMIN")
                .get()
                .get()
                .getDocuments();
        if (documentsInBatch.isEmpty()) {
            return;
        }
        documentsInBatch.forEach(

                document -> writeBatch.update(document.getReference(), "status", "READ"));

        writeBatch.commit().get();
    }

    public void readMessagesAdmin(String userName) throws ExecutionException, InterruptedException {
        Firestore bdFirestore = FirestoreClient.getFirestore();
        WriteBatch writeBatch = bdFirestore.batch();
        List<QueryDocumentSnapshot> documentsInBatch =bdFirestore.collection("chat")
                .document(userName).collection("messages")
                .whereEqualTo("status", "RECEIVED")
                .whereEqualTo("sentBy", userName)
                .get()
                .get()
                .getDocuments();
        if (documentsInBatch.isEmpty()) {
            return;
        }
        documentsInBatch.forEach(

                document -> writeBatch.update(document.getReference(), "status", "READ"));

        writeBatch.commit().get();
    }

    public List<Message> getMessages(String userName){
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference collectionReference =dbFirestore.collection("chat").document("0708252166").collection("messages");
        //DocumentReference documentReference=dbFirestore.collection("chat").document(userName);
        //assert queryDocumentSnapshots != null;
        List<Message> messages= new ArrayList<>();
        collectionReference.addSnapshotListener((queryDocumentSnapshots, e) -> {

            for (DocumentSnapshot doc : queryDocumentSnapshots) {
                Message message = doc.toObject(Message.class);
                message=new Message("hey","j",new Date(), "read");
                messages.add(message);
                logger.info("one");

            }


        });
        messages.add(new Message("hey","j",new Date(), "read"));
        return  messages;
       }

public String uploadImage(MultipartFile myFile) throws IOException {
    InputStream file =  new BufferedInputStream(myFile.getInputStream());
    StorageClient.getInstance().bucket()
    .create("profile/"+ myFile.getOriginalFilename(), file);
    return "success";
}

    public String getUrl(MultipartFile myFile) {
        return "111";
    }
}
