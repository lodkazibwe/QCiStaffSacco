package com.qualitychemicals.qciss.firebase.notification;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.WriteBatch;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class NotificationService {
    public Notification sendNotification(Notification notification){
        Date date =new Date();
        notification.setDate(date);
        Firestore bdFirestore = FirestoreClient.getFirestore();
        bdFirestore.collection("notifications")
                .document(notification.getSentTo()).collection("notifications").document()
                .set(notification);
        return notification;
    }

    public void sendNotification(String userName, String text, Subject subject){

        Notification notification=new Notification();
        notification.setText(text);
        notification.setSentTo(userName);
        notification.setStatus(NotificationStatus.RECEIVED);
        notification.setSubject(subject);
        sendNotification(notification);
    }

    public void readNotifications() throws ExecutionException, InterruptedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        Firestore bdFirestore = FirestoreClient.getFirestore();
        WriteBatch writeBatch = bdFirestore.batch();
        List<QueryDocumentSnapshot> documentsInBatch =bdFirestore.collection("notifications")
                .document(userName).collection("notifications")
                .whereEqualTo("status", "RECEIVED")
                .get()
                .get()
                .getDocuments();
        if (documentsInBatch.isEmpty()) {
           return;
        }
        documentsInBatch.forEach(
                document -> writeBatch.update(document.getReference(), "status", "READ"));

        writeBatch.commit().get();

        //totalUpdates += documentsInBatch.size();
    }

    public void readNotificationsAdmin() throws ExecutionException, InterruptedException {

        Firestore bdFirestore = FirestoreClient.getFirestore();
        WriteBatch writeBatch = bdFirestore.batch();
        List<QueryDocumentSnapshot> documentsInBatch =bdFirestore.collection("notifications")
                .document("ADMIN").collection("notifications")
                .whereEqualTo("status", "RECEIVED")
                .get()
                .get()
                .getDocuments();
        if (documentsInBatch.isEmpty()) {
            return;
        }
        documentsInBatch.forEach(
                document -> writeBatch.update(document.getReference(), "status", "READ"));

        writeBatch.commit().get();

        //totalUpdates += documentsInBatch.size();
    }



}
