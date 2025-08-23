package com.example.OrderService.Clients;

import com.example.OrderService.DTO.ProductQuantityRequest;
import com.example.OrderService.DTO.ProductResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PutExchange;

import java.util.List;

@HttpExchange
public interface ProductServiceClient {

    @GetExchange("/api/products/{id}")
    ProductResponse getProductDetails(@PathVariable int id);

    @PutExchange("/api/products/quantity")
    String updateProductQuantity(@RequestBody List<ProductQuantityRequest> productQuantityRequests);


}
