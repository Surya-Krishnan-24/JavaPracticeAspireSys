package org.example.test.DTO;


import lombok.Data;


import java.math.BigDecimal;

@Data
public class CartItemResponse {

    private int id;
    private UserResponse user;
    private ProductResponse product;
    private int quantity;
    private BigDecimal price;
}
