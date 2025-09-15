package com.example.OrderService.Controller;


import com.example.OrderService.DTO.OrderResponse;
import com.example.OrderService.DTO.OrderResponseSeller;
import com.example.OrderService.Model.OrderStatus;
import com.example.OrderService.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @AuthenticationPrincipal Jwt jwt) {
        String userId = orderService.getUserId(jwt);
        if(userId.equals("Service Down")){
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
        return orderService.createOrder(userId).filter(orderResponse -> orderResponse.getId() != 0)
                .map(orderResponse -> new ResponseEntity<>(orderResponse, HttpStatus.CREATED))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrder(
            @AuthenticationPrincipal Jwt jwt) {
        List<OrderResponse> orderResponse;
        String userId = orderService.getUserId(jwt);
        orderResponse = orderService.getOrder(userId);

        if (orderResponse != null){
            return new ResponseEntity<>(orderResponse,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @PreAuthorize("hasRole('SELLER')")
    @GetMapping("/{sellerName}")
    public ResponseEntity<List<OrderResponseSeller>> getOrdersForSeller(@PathVariable String sellerName) {

        List<OrderResponseSeller> orderResponse = orderService.getOrdersForSeller(sellerName);

        if (orderResponse != null){
            return new ResponseEntity<>(orderResponse,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PreAuthorize("hasRole('SELLER')")
    @PutMapping("/orderstatus/{id}")
    public ResponseEntity<String> updateOrderStatus(@PathVariable int id, @RequestBody OrderStatus status) {

        String orderstatus = orderService.updateOrderStatus(id, status);

        if (orderstatus.equals("UPDATED")){
            return new ResponseEntity<>(orderstatus, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}