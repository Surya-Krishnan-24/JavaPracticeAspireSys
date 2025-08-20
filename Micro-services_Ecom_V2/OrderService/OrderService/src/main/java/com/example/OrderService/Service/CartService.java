package com.example.OrderService.Service;

import com.example.OrderService.Clients.ProductServiceClient;
import com.example.OrderService.Clients.UserServiceClient;
import com.example.OrderService.DOA.CartItemRepo;
import com.example.OrderService.DTO.CartItemRequest;
import com.example.OrderService.DTO.ProductResponse;

import com.example.OrderService.Model.CartItem;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
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

    public boolean addToCart(String userId, CartItemRequest cartItemRequest) {

        ProductResponse productResponse = productServiceClient.getProductDetails(cartItemRequest.getProductId());
        if (productResponse == null || productResponse.getStockQuantity() < cartItemRequest.getQuantity() || userServiceClient.getUserDetails(userId) == null) {
            return false;
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

    public boolean deleteItemFromCart(String userId, int productId) {
        CartItem cartItem = cartItemRepo.findByUserIdAndProductId(userId, productId);

        if (cartItem != null){
            cartItemRepo.delete(cartItem);
            return true;

        }
        return false;
    }

    public List<CartItem> getCart(String userId) {
        return cartItemRepo.findByUserId(userId);
    }

    public void clearCart(String userId) {
        cartItemRepo.deleteByUserId(userId);
    }
}