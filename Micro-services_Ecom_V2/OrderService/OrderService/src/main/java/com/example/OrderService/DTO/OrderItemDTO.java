package com.example.OrderService.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private int id;
    private int productid;
    private int quantity;
    private BigDecimal price;
    private BigDecimal total;


}
