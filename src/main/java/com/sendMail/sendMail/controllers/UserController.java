package com.sendMail.sendMail.controllers;

import com.sendMail.sendMail.dtos.requests.user.*;
import com.sendMail.sendMail.dtos.responses.ApiResponse;
import com.sendMail.sendMail.exceptions.*;
import com.sendMail.sendMail.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/sendmail")
public class UserController {
        @Autowired
        private UserService userService;
        @PostMapping("/users/register")

        public ResponseEntity<?> createUser(@RequestBody UserRequest request) {
            try {
                return new ResponseEntity<>(userService.register(request), HttpStatus.CREATED);
            } catch (SendEmailException error) {
                return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), HttpStatus.BAD_REQUEST);
            }
        }

        @PostMapping("/users/login")

        public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
            try{
                return new ResponseEntity<>(userService.login(loginRequest),HttpStatus.FOUND);
            }catch (UserNotFoundException | IncorrectLoginDetailsException error){
                return new ResponseEntity<>(new ApiResponse(false, error.getMessage()),HttpStatus.BAD_REQUEST);

            }
        }

        @GetMapping("/users/logout/{emailAddress}")

        public ResponseEntity<?> logout(@PathVariable String emailAddress){
            try{
                return new ResponseEntity<>(userService.logout(emailAddress),HttpStatus.OK);
            }catch (UserNotFoundException  error){
                return new ResponseEntity<>(new ApiResponse(false, error.getMessage()),HttpStatus.BAD_REQUEST);
            }
        }
        @GetMapping("/admin/search/{emailAddress}")
        public ResponseEntity<?> findUserByUsername(@PathVariable String emailAddress){
            try{
                return new ResponseEntity<>(userService.findByEmailAddress(emailAddress),HttpStatus.FOUND);
            }catch (UserNotFoundException error){
                return new ResponseEntity<>(new ApiResponse(false,error.getMessage()),HttpStatus.BAD_REQUEST);
            }
        }


        @PatchMapping("/users/update/{emailAddress}")
        public ResponseEntity<?> updateUserDetails(@RequestBody UpdateDetailsRequest updateAccountRequest, @PathVariable String emailAddress) {
            try{
                return new ResponseEntity<>(userService.updateUserDetails(updateAccountRequest, emailAddress),HttpStatus.CREATED);
            }catch (UserNotFoundException | EmailAlreadyExistsException |IncorrectLoginDetailsException error){
                return new ResponseEntity<>(new ApiResponse(false,error.getMessage()),HttpStatus.BAD_REQUEST);
            }
        }

        @PatchMapping("/users/updatePassword/{emailAddress}")
        public ResponseEntity<?> updateUserPassword(@RequestBody UpdatePasswordRequest updatePasswordRequest, @PathVariable String emailAddress) {
            try{
                return new ResponseEntity<>(userService.updatePassword(updatePasswordRequest, emailAddress),HttpStatus.CREATED);
            }catch (UserNotFoundException | UnMatchingDetailsException error){
                return new ResponseEntity<>(new ApiResponse(false,error.getMessage()),HttpStatus.BAD_REQUEST);
            }
        }

        @DeleteMapping("users/delete/{emailAddress}")
        public ResponseEntity<?> deleteUser(@RequestBody DeleteRequest deleteRequest, @PathVariable String emailAddress) {
            try{
                return new ResponseEntity<>(userService.deleteAccount(deleteRequest),HttpStatus.CREATED);
            }catch (UserNotFoundException | UnMatchingDetailsException error){
                return new ResponseEntity<>(new ApiResponse(false,error.getMessage()),HttpStatus.BAD_REQUEST);
            }
        }


    }

