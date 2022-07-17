package com.sendMail.sendMail.controllers;

import com.sendMail.sendMail.datas.models.Message;
import com.sendMail.sendMail.dtos.requests.message.SendManyMessageRequest;
import com.sendMail.sendMail.dtos.requests.user.*;
import com.sendMail.sendMail.dtos.responses.ApiResponse;
import com.sendMail.sendMail.dtos.responses.message.SendMessageResponse;
import com.sendMail.sendMail.exceptions.*;
import com.sendMail.sendMail.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/sendmail")
public class MessageController {


        @Autowired
        private MessageService messageService;
        @PostMapping("/message/send")

        public ResponseEntity<?> sendAMessage(@RequestBody Message message) {

           SendMessageResponse sendMessageResponse = messageService.send(message);

            return new ResponseEntity<>(new ApiResponse(true, sendMessageResponse.getAlert()), HttpStatus.OK);
        }

        @PostMapping("/messages/send")

        public ResponseEntity<?> sendMessageToMany(@RequestBody SendManyMessageRequest sendManyMessageRequest){
           List<SendMessageResponse> sendMessageResponses = messageService.sendToMany(sendManyMessageRequest);
           List<String> apiResponse = new ArrayList<>();
            for ( SendMessageResponse response : sendMessageResponses) {
                apiResponse.add(response.getAlert());
            }
           return new ResponseEntity<>(new ApiResponse(true, apiResponse.toString()),HttpStatus.CREATED);
            }

        @GetMapping("/messages/forward/{messageId}")

        public ResponseEntity<?> forwardMessage(@RequestBody SendManyMessageRequest sendManyMessageRequest, @PathVariable String messageId){
            List<SendMessageResponse> sendMessageResponses = messageService.forward(sendManyMessageRequest, messageId);
            List<String> apiResponse = new ArrayList<>();
            for ( SendMessageResponse response : sendMessageResponses) {
                apiResponse.add(response.getAlert());
            }
            return new ResponseEntity<>(new ApiResponse(true, apiResponse.toString()),HttpStatus.CREATED);
            }
        @GetMapping("/messages/read/{messageId}")
        public ResponseEntity<?> readMessage(@PathVariable String messageId){
          Message message = messageService.read(messageId);
             return new ResponseEntity<>(new ApiResponse(true,message.getMessageBody()),HttpStatus.OK);

        }



}
