package com.example.OrderService.DTO;

import lombok.Data;

@Data
public class ProductQuantityRequest {
    private int productId;
    private int stockQuantity;

    public ProductQuantityRequest(int productId, int quantity) {
        this.productId = productId;
        this.stockQuantity = quantity;
    }
}
