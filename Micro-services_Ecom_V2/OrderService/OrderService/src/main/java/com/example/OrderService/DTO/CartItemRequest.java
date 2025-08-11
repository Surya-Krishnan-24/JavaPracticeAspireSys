package com.example.OrderService.DTO;

import lombok.Data;

@Data
public class CartItemRequest {
    private int productId;
    private int quantity;

}
