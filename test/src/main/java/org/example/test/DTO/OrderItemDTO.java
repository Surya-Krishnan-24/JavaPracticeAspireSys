package org.example.test.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDTO {
    private int id;
    private int productid;
    private int quantity;
    private BigDecimal price;

}
