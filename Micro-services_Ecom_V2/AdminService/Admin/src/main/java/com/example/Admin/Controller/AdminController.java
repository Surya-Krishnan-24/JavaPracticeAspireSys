package com.example.Admin.Controller;

import com.example.Admin.Clients.ProductServiceClient;
import com.example.Admin.Clients.UserServiceClient;
import com.example.Admin.DTO.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private final UserServiceClient userServiceClient;
    private final ProductServiceClient productServiceClient;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<String> createAdmin(@RequestBody UserRequest userRequest) {
        return userServiceClient.createAdmin(userRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/allusers")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return userServiceClient.getAllUsers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String id) {
        return userServiceClient.getUser(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{id}")
    public ResponseEntity<UserRequest> updateUser(@PathVariable String id, @RequestBody UserRequest userRequest) {
        return userServiceClient.updateUser(id, userRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/keycloak/{keycloakId}")
    public String getUserIdByKeycloakId(@PathVariable String keycloakId) {
        return userServiceClient.getUserDetailsByKeycloak(keycloakId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return productServiceClient.getAllProducts();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/products/seller/{sellerName}")
    public ResponseEntity<List<ProductResponse>> getAllProductsBySeller(@PathVariable String sellerName) {
        return productServiceClient.getAllProductsBySeller(sellerName);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable int id) {
        return productServiceClient.getProductById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/products/{id}/seller")
    public ResponseEntity<ProductResponse> getProductByIdOfSeller(@PathVariable int id, @RequestParam String sellerName) {
        return productServiceClient.getProductByIdOfSeller(id, sellerName);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable int id, @RequestBody ProductRequest productRequest) {
        return productServiceClient.updateProduct(id, productRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable int id) {
        return productServiceClient.deleteProduct(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/products/search")
    public ResponseEntity<List<ProductResponse>> searchProduct(@RequestParam String keyword) {
        return productServiceClient.searchProduct(keyword);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/products/quantity")
    public ResponseEntity<String> updateProductQuantity(@RequestBody List<ProductQuantityRequest> productQuantityRequests) {
        return productServiceClient.updateProductQuantity(productQuantityRequests);
    }
}

