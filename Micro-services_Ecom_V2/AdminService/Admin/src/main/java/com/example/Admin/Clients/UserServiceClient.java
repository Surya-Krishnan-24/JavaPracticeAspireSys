package com.example.Admin.Clients;

import com.example.Admin.DTO.UserRequest;
import com.example.Admin.DTO.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

import java.util.List;

@HttpExchange
public interface UserServiceClient {

    @GetExchange("/api/users")
    ResponseEntity<List<UserResponse>> getAllUsers();

    @PostExchange("/api/users/admin")
    ResponseEntity<String> createAdmin(@RequestBody UserRequest userRequest);

    @GetExchange("/api/users/{id}")
    ResponseEntity<UserResponse> getUser(@PathVariable String id);

    @PutExchange("/api/users/{id}")
    ResponseEntity<UserRequest> updateUser(@PathVariable String id,@RequestBody UserRequest userRequest);

    @GetExchange("/api/users/keycloak/{keycloakId}")
    String getUserDetailsByKeycloak(@PathVariable String keycloakId);
}
