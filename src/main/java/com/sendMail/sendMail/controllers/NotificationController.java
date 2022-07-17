package com.sendMail.sendMail.controllers;

import com.sendMail.sendMail.datas.models.Message;
import com.sendMail.sendMail.datas.models.Notification;
import com.sendMail.sendMail.dtos.responses.ApiResponse;
import com.sendMail.sendMail.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/sendmail")
public class NotificationController {

        @Autowired
        private NotificationService notificationService;
        @PostMapping("/notifications/create")

        public ResponseEntity<?> createNotification(@RequestBody Message message) {
            Notification notification = notificationService.create(message);
                return new ResponseEntity<>(new ApiResponse(true, notification.getTitle()), HttpStatus.CREATED);
        }

        @GetMapping("/notifications/read/{messageId}")

        public ResponseEntity<?> read(@PathVariable String messageId){
            Notification notification = notificationService.read(messageId);
                return new ResponseEntity<>(new ApiResponse(true, notification.getTitle()),HttpStatus.OK);

        }

}
