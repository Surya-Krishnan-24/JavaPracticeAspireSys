package com.example.Notification.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NotificationOrderResponse {

    private String userName;
    private String name;
    private BigDecimal price;
    private int Quantity;
    private BigDecimal Total;
}
