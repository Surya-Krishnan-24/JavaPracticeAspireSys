package com.example.Admin.Clients;

import com.example.Admin.DTO.ProductQuantityRequest;
import com.example.Admin.DTO.ProductRequest;
import com.example.Admin.DTO.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PutExchange;

import java.util.List;

@HttpExchange("/api/products")
public interface ProductServiceClient {

    @GetExchange
    ResponseEntity<List<ProductResponse>> getAllProducts();

    @GetExchange("/seller/{sellerName}")
    ResponseEntity<List<ProductResponse>> getAllProductsBySeller(@PathVariable String sellerName);

    @GetExchange("/{id}")
    ResponseEntity<ProductResponse> getProductById(@PathVariable int id);

    @GetExchange("/{id}/seller")
    ResponseEntity<ProductResponse> getProductByIdOfSeller(@PathVariable int id, @RequestParam String sellerName);

    @PutExchange("/{id}")
    ResponseEntity<ProductResponse> updateProduct(@PathVariable int id, @RequestBody ProductRequest productRequest);

    @DeleteExchange("/admin/{id}")
    ResponseEntity<Boolean> deleteProduct(@PathVariable int id);

    @GetExchange("/admin/search")
    ResponseEntity<List<ProductResponse>> searchProduct(@RequestParam String keyword);

    @PutExchange("/quantity")
    ResponseEntity<String> updateProductQuantity(@RequestBody List<ProductQuantityRequest> productQuantityRequests);
}
