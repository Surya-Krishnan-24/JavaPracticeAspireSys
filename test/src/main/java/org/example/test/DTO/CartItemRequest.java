package org.example.test.DTO;

import lombok.Data;

@Data
public class CartItemRequest {
    private int productId;
    private int quantity;

}
