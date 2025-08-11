package com.example.OrderService.Service;


import com.example.OrderService.DOA.CartItemRepo;
import com.example.OrderService.DTO.CartItemRequest;
import com.example.OrderService.Model.CartItem;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class CartService {
    @Autowired
    private CartItemRepo cartItemRepo;

    public boolean addToCart(int userId, CartItemRequest cartItemRequest) {
//        // Look for product
//        Optional<Product> productOpt = productRepository.findById(request.getProductId());
//        if (productOpt.isEmpty())
//            return false;
//
//        Product product = productOpt.get();
//        if (product.getStockQuantity() < request.getQuantity())
//            return false;
//
//        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
//        if (userOpt.isEmpty())
//            return false;
//
//        User user = userOpt.get();

        CartItem existingCartItem = cartItemRepo.findByUserIdAndProductId(userId, cartItemRequest.getProductId());
        if (existingCartItem != null) {

            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemRequest.getQuantity());
            existingCartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepo.save(existingCartItem);
        } else {

            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(cartItemRequest.getProductId());
            cartItem.setQuantity(cartItemRequest.getQuantity());
            cartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepo.save(cartItem);
        }
        return true;
    }

    public boolean deleteItemFromCart(int userId, int productId) {
        CartItem cartItem = cartItemRepo.findByUserIdAndProductId(userId, productId);

        if (cartItem != null){
            cartItemRepo.delete(cartItem);
            return true;
        }
        return false;
    }

    public List<CartItem> getCart(int userId) {
        return cartItemRepo.findByUserId(userId);
    }

    public void clearCart(int userId) {
        cartItemRepo.deleteByUserId(userId);
    }
}