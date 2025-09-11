package com.example.Seller.Clients;

import com.example.Seller.DTO.ProductQuantityRequest;
import com.example.Seller.DTO.ProductRequest;
import com.example.Seller.DTO.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.*;

import java.util.List;

@HttpExchange("/api/products")
public interface ProductServiceClient {

    @GetExchange("/seller/{sellerName}")
    ResponseEntity<List<ProductResponse>> getAllProductsBySeller(@PathVariable String sellerName);

    @GetExchange("/seller/{id}/{sellerName}")
    ResponseEntity<ProductResponse> getProductByIdOfSeller(@PathVariable int id, @PathVariable String sellerName);

    @PutExchange("/{id}/seller/{sellerName}")
    ResponseEntity<ProductResponse> updateProductBySeller(@PathVariable int id, @PathVariable String sellerName, @RequestBody ProductRequest productRequest);

    @PostExchange("/seller/{sellerName}")
    ResponseEntity<ProductResponse> createProductBySeller(
            @PathVariable String sellerName,
            @RequestBody ProductRequest productRequest,
            @RequestHeader("email") String email
    );

    @DeleteExchange("/seller/{id}/{seller}")
    ResponseEntity<Boolean> deleteProductBySeller(@PathVariable int id, @PathVariable String seller);

    @GetExchange("/search/seller/{sellerName}")
    ResponseEntity<List<ProductResponse>> searchProductBySeller(@RequestParam String keyword, @PathVariable String sellerName);

    @PutExchange("/quantity/seller/{sellerName}")
    ResponseEntity<String> updateProductQuantityBySeller(@PathVariable String sellerName, @RequestBody List<ProductQuantityRequest> productQuantityRequests);


}
