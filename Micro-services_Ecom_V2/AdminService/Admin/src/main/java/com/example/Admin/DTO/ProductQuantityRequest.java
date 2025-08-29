package com.example.Admin.DTO;

import lombok.Data;

@Data
public class ProductQuantityRequest {
    private int productId;
    private int stockQuantity;
}
