package com.example.ProductService.DTO;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class ProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private int stockQuantity;
    private String category;
    private String imageUrl;
    private String seller;
}
