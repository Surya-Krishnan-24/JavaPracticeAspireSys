package com.example.Seller.DTO;

import lombok.Data;

@Data
public class ProductsOrderResponse {
    private int productId;
    private String productName;
    private int quantity;
    private int price;
}
