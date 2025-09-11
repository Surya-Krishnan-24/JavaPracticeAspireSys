package com.example.OrderService.Clients;

import com.example.OrderService.DTO.UserAddress;
import com.example.OrderService.DTO.UserResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface UserServiceClient {

    @GetExchange("/api/users/{id}")
    UserResponse getUser(@PathVariable String id);

    @GetExchange("/api/users/keycloak/{id}")
    String getUserDetailsByKeycloak(@PathVariable String id);

    @GetExchange("/api/users/fullname/{id}")
    String getUsername(@PathVariable String id);

    @GetExchange(("/api/users/address/{id}"))
    UserAddress getUserAddress(@PathVariable String id);

    @GetExchange("/api/users/email/{id}")
    String getUserEmail(@PathVariable String id);
}
