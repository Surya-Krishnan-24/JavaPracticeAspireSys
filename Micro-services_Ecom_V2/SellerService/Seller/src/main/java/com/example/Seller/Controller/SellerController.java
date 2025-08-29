package com.example.Seller.Controller;

import com.example.Seller.Clients.ProductServiceClient;
import com.example.Seller.Clients.UserServiceClient;
import com.example.Seller.DTO.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/seller")
@AllArgsConstructor
public class SellerController {

    private final UserServiceClient userServiceClient;
    private final ProductServiceClient productServiceClient;

    @PostMapping("/register")
    public ResponseEntity<String> createSeller(@RequestBody UserRequest userRequest) {

        return userServiceClient.createSeller(userRequest);
    }

    @PreAuthorize("hasRole('SELLER')")
    @GetMapping("/products/{sellerName}")
    public ResponseEntity<List<ProductResponse>> getAllProductsBySeller(@PathVariable String sellerName) {
        return productServiceClient.getAllProductsBySeller(sellerName);
    }

    @PreAuthorize("hasRole('SELLER')")
    @GetMapping("/products/{id}/{sellerName}")
    public ResponseEntity<ProductResponse> getProductByIdOfSeller(@PathVariable int id, @PathVariable String sellerName) {
        return productServiceClient.getProductByIdOfSeller(id, sellerName);
    }

    @PreAuthorize("hasRole('SELLER')")
    @PostMapping("/products/{sellerName}")
    public ResponseEntity<ProductResponse> createProduct(@PathVariable String sellerName, @RequestBody ProductRequest productRequest) {
        String email = userServiceClient.getUserBySellerName(sellerName);
        System.out.println(email);
        return productServiceClient.createProductBySeller(sellerName, productRequest, email);
    }


    @PreAuthorize("hasRole('SELLER')")
    @PutMapping("/products/{id}/{sellerName}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable int id, @PathVariable String sellerName, @RequestBody ProductRequest productRequest) {
        return productServiceClient.updateProductBySeller(id, sellerName, productRequest);
    }

    @PreAuthorize("hasRole('SELLER')")
    @DeleteMapping("/products/{id}/{sellerName}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable int id, @PathVariable String sellerName) {
        return productServiceClient.deleteProductBySeller(id, sellerName);
    }

    @PreAuthorize("hasRole('SELLER')")
    @GetMapping("/products/search/{sellerName}")
    public ResponseEntity<List<ProductResponse>> searchProduct(@PathVariable String sellerName, @RequestParam String keyword) {
        return productServiceClient.searchProductBySeller(keyword, sellerName);
    }

    @PreAuthorize("hasRole('SELLER')")
    @PutMapping("/products/quantity/{sellerName}")
    public ResponseEntity<String> updateProductQuantity(@PathVariable String sellerName, @RequestBody List<ProductQuantityRequest> productQuantityRequests) {
        return productServiceClient.updateProductQuantityBySeller(sellerName, productQuantityRequests);
    }
}
