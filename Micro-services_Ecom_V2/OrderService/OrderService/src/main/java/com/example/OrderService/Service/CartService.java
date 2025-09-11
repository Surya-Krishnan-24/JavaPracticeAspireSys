package com.example.OrderService.Service;

import com.example.OrderService.Clients.ProductServiceClient;
import com.example.OrderService.Clients.UserServiceClient;
import com.example.OrderService.DOA.CartItemRepo;
import com.example.OrderService.DTO.CartItemRequest;
import com.example.OrderService.DTO.ProductResponse;

import com.example.OrderService.GlobalExceptionHandler.ResourceNotFoundException;
import com.example.OrderService.Model.CartItem;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepo cartItemRepo;

    private final ProductServiceClient productServiceClient;

    private final UserServiceClient userServiceClient;

    public boolean addToCart( String userId, CartItemRequest cartItemRequest) {

        ProductResponse productResponse = productServiceClient.getProductDetails(cartItemRequest.getProductId());
        if (productResponse == null) {
            throw new ResourceNotFoundException("Product not found");
        }

        if (productResponse.getStockQuantity() < cartItemRequest.getQuantity()) {
            throw new ResourceNotFoundException("Insufficient stock for the product");
        }
        if (userServiceClient.getUser(userId) == null) {
            throw new ResourceNotFoundException("User not found");
        }

        CartItem existingCartItem = cartItemRepo.findByUserIdAndProductId(userId, cartItemRequest.getProductId());
        if (existingCartItem != null) {

            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemRequest.getQuantity());
            existingCartItem.setPrice(productResponse.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRepo.save(existingCartItem);
        } else {

            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(cartItemRequest.getProductId());
            cartItem.setQuantity(cartItemRequest.getQuantity());
            cartItem.setPrice(productResponse.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            cartItemRepo.save(cartItem);
        }
        return true;
    }

    public void deleteItemFromCart(String userId, int productId) {
        CartItem cartItem = cartItemRepo.findByUserIdAndProductId(userId, productId);


        if (cartItem == null) {
            throw new ResourceNotFoundException("Cart item not found");
        }

        cartItemRepo.delete(cartItem);
    }

    public List<CartItem> getCart(String userId) {
        return cartItemRepo.findByUserId(userId);
    }

    public void clearCart(String userId) {
        cartItemRepo.deleteByUserId(userId);
    }

    public String getUserId(Jwt jwt) {
        String keycloakId = jwt.getSubject();
        return userServiceClient.getUserDetailsByKeycloak(keycloakId);

    }
}