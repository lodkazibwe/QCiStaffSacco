package com.qualitychemicals.qciss.firebase.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RequestMapping("/notifications")
@RestController
public class NotificationController {
    @Autowired NotificationService notificationService;

   /* @PostMapping("/send")
    public ResponseEntity<Notification> sendNote(@Valid @RequestBody Notification notification){
        return new ResponseEntity<>(notificationService.sendNotification(notification), HttpStatus.OK);
    }*/


    @PutMapping("/readNotifications}")
    public void readNotifications(@PathVariable String userName) throws ExecutionException, InterruptedException {
        notificationService.readNotifications();
       // return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PutMapping("/admin/readNotifications")
    public void readNotificationsAdmin(@PathVariable String userName) throws ExecutionException, InterruptedException {
        notificationService.readNotificationsAdmin();
        // return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
