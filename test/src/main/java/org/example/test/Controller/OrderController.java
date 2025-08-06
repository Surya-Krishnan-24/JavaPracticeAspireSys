package org.example.test.Controller;

import org.example.test.DTO.OrderResponse;
import org.example.test.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestHeader("X-User-ID") String userId){
        OrderResponse order = orderService.createOrder(userId);
        return order!= null
                ? new ResponseEntity<>(order, HttpStatus.CREATED)
                : new ResponseEntity<>("No Cart Items found", HttpStatus.BAD_REQUEST);
    }
}
