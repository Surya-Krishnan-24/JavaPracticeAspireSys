package com.example.OrderService.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NotificationOrderResponse {

    private String name;
    private BigDecimal price;
    private int Quantity;
    private BigDecimal Total;
}
