package com.example.OrderService.DTO;



import lombok.Data;

import java.util.List;

@Data
public class OrderResponseSeller {

    private int orderID;
    private String name;
    private UserAddress addressResponse;
    private List<ProductsOrderResponse> productsOrderResponses;
    private String orderStatus;

}
