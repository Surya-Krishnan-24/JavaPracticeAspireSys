package com.example.Insta.Controller;


import com.example.Insta.DTO.UserDTO;

import com.example.Insta.DTO.UserUpdateDTO;
import com.example.Insta.Model.User;
import com.example.Insta.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDTO userDTO){
        userService.addUser(userDTO);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody UserDTO userDTO){
        userService.signInUser(userDTO);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping(value = "/updateprofile/{username}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<User> updateProfile(@PathVariable String username, @RequestPart("userDTO") UserUpdateDTO userDTO, @RequestPart("image") MultipartFile image){
        User updatedUser = userService.updateProfileData(username,userDTO,image);
        return new ResponseEntity<>(updatedUser,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteaccount/{id}")
    public ResponseEntity<User> deleteAccount(@PathVariable int id){
        String status = userService.delete(id);
        if (status.equals("Deleted")){
            return new ResponseEntity<>(new User(),HttpStatus.OK);
        }
        return new ResponseEntity<>(new User(),HttpStatus.NOT_FOUND);
    }



}
