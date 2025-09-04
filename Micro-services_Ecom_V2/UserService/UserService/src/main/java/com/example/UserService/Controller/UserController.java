package com.example.UserService.Controller;


import com.example.UserService.DTO.UserLoginRequest;
import com.example.UserService.DTO.UserRequest;
import com.example.UserService.DTO.UserResponse;
import com.example.UserService.Model.UserRole;
import com.example.UserService.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {


    private final UserService userService;


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){

        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest){
        userRequest.setUserRole(UserRole.USER);
        return new ResponseEntity<>(userService.createUser(userRequest), HttpStatus.CREATED);

    }

    @PostMapping("/seller")
    public ResponseEntity<String> createSeller(@RequestBody UserRequest userRequest){
        userRequest.setUserRole(UserRole.SELLER);
        return new ResponseEntity<>(userService.createUser(userRequest), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin")
    public ResponseEntity<String> createAdmin(@RequestBody UserRequest userRequest){

        userRequest.setUserRole(UserRole.ADMIN);
        return new ResponseEntity<>(userService.createUser(userRequest), HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginRequest userLoginRequest){

        return new ResponseEntity<>(userService.loginUser(userLoginRequest),HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String id){

        log.info("Request received for user: {}", id);
        UserResponse response =  userService.getUserById(id);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('SELLER')")
    @GetMapping("/seller/{sellername}")
    public String getUserBySellerName(@PathVariable String sellername){
        String user = userService.getUserBySellerName(sellername);
        System.out.println(user);
        return user;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PutMapping("/{id}")
    public ResponseEntity<UserRequest> updateUser(@PathVariable String id,@RequestBody UserRequest userRequest){
        UserRequest user1 = userService.updateUser(id,userRequest);
        if(user1 == null) {
            return new ResponseEntity<>(user1, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user1,HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/keycloak/{keycloakId}")
    public String getUserDetailsByKeycloak(@PathVariable String keycloakId){
        return userService.getUserIdByKeyCloak(keycloakId);
    }

}
