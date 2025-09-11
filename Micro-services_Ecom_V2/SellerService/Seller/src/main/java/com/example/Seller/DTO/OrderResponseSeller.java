package com.example.Seller.DTO;

import lombok.Data;

import java.util.List;

@Data
public class OrderResponseSeller {

    private String orderID;
    private String name;
    private AddressResponse addressResponse;
    private List<ProductsOrderResponse> productsOrderResponses;
    private String orderStatus;
}
