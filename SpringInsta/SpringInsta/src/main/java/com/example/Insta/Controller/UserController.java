package com.example.Insta.Controller;


import com.example.Insta.DTO.UserDTO;

import com.example.Insta.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDTO userDTO){
        String response = userService.addUser(userDTO);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signin(@RequestBody UserDTO userDTO){
        String response = userService.signInUser(userDTO);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

}
