package com.example.OrderService.DTO;

import com.example.OrderService.Model.OrderStatus;
import lombok.Data;

import java.util.List;

@Data
public class OrderUpdatedEvent {
    private int orderId;
    private String userName;
    private String userEmail;
    private List<String> productNames;
    private OrderStatus orderStatus;
}
