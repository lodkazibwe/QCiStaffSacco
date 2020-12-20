package com.qualitychemicals.qciss.firebase.message;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.WriteBatch;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ChatService {

    public Message sendMessage(MessageDto messageDto){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String sender=auth.getName();
        Date date =new Date();
        Message message=new Message();
        message.setText(messageDto.getText());
        message.setSentBy(sender);
        message.setSentAt(date);
        message.setStatus(Status.RECEIVED);

        Firestore bdFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture =bdFirestore.collection("chat")
                .document(sender).collection("messages").document()
                .set(message);
        return message;
    }

    public Message sendMessage(MessageDto messageDto, String userName ){

        Message message=new Message();
        Date date =new Date();
        message.setText(messageDto.getText());
        message.setSentAt(date);
        message.setSentBy("ADMIN");
        message.setStatus(Status.RECEIVED);

        Firestore bdFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture =bdFirestore.collection("chat")
                .document(userName).collection("messages").document()
                .set(message);
        return message;
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