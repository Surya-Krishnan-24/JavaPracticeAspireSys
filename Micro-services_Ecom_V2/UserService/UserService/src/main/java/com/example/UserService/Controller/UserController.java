package com.example.UserService.Controller;


import com.example.UserService.DTO.UserRequest;
import com.example.UserService.DTO.UserResponse;
import com.example.UserService.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {


    private final UserService userService;


    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){

        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest){

        return new ResponseEntity<>(userService.createUser(userRequest), HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String id){

        log.info("Request received for user: {}", id);
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() ->ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserRequest> updateUser(@PathVariable String id,@RequestBody UserRequest userRequest){
        UserRequest user1 = userService.updateUser(id,userRequest);
        if(user1 == null) {
            return new ResponseEntity<>(user1, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user1,HttpStatus.ACCEPTED);
    }

}
