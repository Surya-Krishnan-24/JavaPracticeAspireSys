package com.example.Seller.Clients;

import com.example.Seller.DTO.OrderResponseSeller;
import com.example.Seller.DTO.OrderStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PutExchange;

import java.util.List;

@HttpExchange("/orders")
public interface OrderServiceClient {

    @GetExchange("/{sellerName}")
    ResponseEntity<List<OrderResponseSeller>> getOrdersForSeller(@PathVariable String sellerName);

    @PutExchange("/orderstatus/{id}")
    ResponseEntity<String> updateOrderStatus(@PathVariable int id,@RequestBody OrderStatus status);
}
