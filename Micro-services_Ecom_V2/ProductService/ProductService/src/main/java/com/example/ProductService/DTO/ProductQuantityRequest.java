package com.example.ProductService.DTO;

import lombok.Data;

@Data
public class ProductQuantityRequest {
    private int productId;
    private int stockQuantity;
}
