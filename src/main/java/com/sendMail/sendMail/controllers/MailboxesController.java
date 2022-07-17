package com.sendMail.sendMail.controllers;

import com.sendMail.sendMail.dtos.requests.user.*;
import com.sendMail.sendMail.dtos.responses.ApiResponse;
import com.sendMail.sendMail.exceptions.*;
import com.sendMail.sendMail.services.MailboxesService;
import com.sendMail.sendMail.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("api/v1/sendmail")
public class MailboxesController {
        @Autowired
        private MailboxesService mailboxesService;
        @PostMapping("/mailboxes/create")

        public ResponseEntity<?> createUser(@RequestBody String emailAddress) {
            try {
                return new ResponseEntity<>(mailboxesService.create(emailAddress), HttpStatus.CREATED);
            } catch (SendEmailException error) {
                return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), HttpStatus.BAD_REQUEST);
            }
        }

        @GetMapping("/mailboxes/inboxes/{emailAddress}")

        public ResponseEntity<?> login(@PathVariable String emailAddress){
            return new ResponseEntity<>(mailboxesService.viewAllInbox(emailAddress),HttpStatus.OK);
            }


        @GetMapping("/mailboxes/outboxes/{emailAddress}")

        public ResponseEntity<?> logout(@PathVariable String emailAddress) {
            return new ResponseEntity<>(mailboxesService.viewAllOutbox(emailAddress), HttpStatus.OK);
        }
}
