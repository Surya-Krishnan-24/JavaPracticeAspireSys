package com.example.Seller.Clients;

import com.example.Seller.DTO.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface UserServiceClient {

    @PostExchange("/api/users/seller")
    ResponseEntity<String> createSeller(@RequestBody UserRequest userRequest);

    @GetExchange("/api/users/seller/{sellername}")
    String getUserBySellerName(@PathVariable String sellername);


}

