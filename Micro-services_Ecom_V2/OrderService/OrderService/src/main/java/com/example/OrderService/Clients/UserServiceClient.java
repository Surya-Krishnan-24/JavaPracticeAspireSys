package com.example.OrderService.Clients;

import com.example.OrderService.DTO.UserResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface UserServiceClient {

    @GetExchange("/api/users/{id}")
    UserResponse getUserDetails(@PathVariable String id);

    @GetExchange("/api/users/keycloak/{id}")
    String getUserDetailsByKeycloak(@PathVariable String id);

}
