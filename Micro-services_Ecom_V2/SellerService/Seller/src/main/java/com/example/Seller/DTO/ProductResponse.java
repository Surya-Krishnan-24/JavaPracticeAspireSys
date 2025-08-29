package com.example.Seller.DTO;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class ProductResponse {

    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private int stockQuantity;
    private String category;
    private String imageUrl;
    private Boolean active;
    private String seller;
}
