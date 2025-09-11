package com.example.Seller.Controller;

import com.example.Seller.Clients.OrderServiceClient;
import com.example.Seller.Clients.ProductServiceClient;
import com.example.Seller.Clients.UserServiceClient;
import com.example.Seller.DTO.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/seller")
@AllArgsConstructor
public class SellerController {

    private final UserServiceClient userServiceClient;
    private final ProductServiceClient productServiceClient;
    private final OrderServiceClient orderServiceClient;

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
    @PostMapping("/product")
    public ResponseEntity<ProductResponse> createProduct(@AuthenticationPrincipal Jwt jwt, @RequestBody ProductRequest productRequest) {
        String sellerName = jwt.getClaimAsString("preferred_username");
        String email = userServiceClient.getUserBySellerName(sellerName);
        return productServiceClient.createProductBySeller(sellerName,productRequest, email);
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


    @PreAuthorize("hasRole('SELLER')")
    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseSeller>> getOrdersForSeller(@AuthenticationPrincipal Jwt jwt) {
        String sellerName = jwt.getClaimAsString("preferred_username");
        return orderServiceClient.getOrdersForSeller(sellerName);
    }

    @PreAuthorize("hasRole('SELLER')")
    @PutMapping("/orders/{id}")
    public ResponseEntity<String> updateOrderStatus(@PathVariable int id,@RequestBody OrderStatus status) {

        return orderServiceClient.updateOrderStatus(id, status);
    }

}
