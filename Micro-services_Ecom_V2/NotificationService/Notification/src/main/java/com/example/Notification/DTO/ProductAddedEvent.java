package com.example.Notification.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data

public class ProductAddedEvent {
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private int stockQuantity;
    private String category;
    private String imageUrl;
    private String seller;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String sellerEmail;
}
