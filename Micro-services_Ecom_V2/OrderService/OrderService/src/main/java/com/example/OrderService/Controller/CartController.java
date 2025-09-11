package com.example.OrderService.Controller;


import com.example.OrderService.DTO.CartItemRequest;
import com.example.OrderService.Model.CartItem;
import com.example.OrderService.Service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<String> addToCart(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody CartItemRequest request) {

        String userId = cartService.getUserId(jwt);
        if (!cartService.addToCart(userId, request)) {
            return ResponseEntity.badRequest().body("Product Out of Stock or User not found or Product not found");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeFromCart(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable int productId) {
        System.out.println(jwt);
        String userId = cartService.getUserId(jwt);
        cartService.deleteItemFromCart(userId, productId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping
    public ResponseEntity<List<CartItem>> getCart(
            @AuthenticationPrincipal Jwt jwt) {
        String userId = cartService.getUserId(jwt);
        return ResponseEntity.ok(cartService.getCart(userId));
    }

}