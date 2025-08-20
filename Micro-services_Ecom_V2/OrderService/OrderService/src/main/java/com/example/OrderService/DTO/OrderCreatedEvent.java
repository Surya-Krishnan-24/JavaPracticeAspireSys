package com.example.OrderService.DTO;


import com.example.OrderService.Model.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {
    private int orderId;
    private String userName;
    private String userEmail;
    private OrderStatus status;
    private List<NotificationOrderResponse> notificationOrderResponses;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;



}
