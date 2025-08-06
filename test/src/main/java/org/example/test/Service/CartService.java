package org.example.test.Service;

import jakarta.transaction.Transactional;
import org.example.test.DOA.CartItemRepo;
import org.example.test.DOA.ProductRepo;
import org.example.test.DOA.UserRepo;
import org.example.test.DTO.*;
import org.example.test.Model.CartItem;
import org.example.test.Model.Product;
import org.example.test.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    CartItemRepo cartItemRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    UserRepo userRepo;

    public boolean addToCart(String userId, CartItemRequest cartItemRequest) {
        Optional<Product> product = productRepo.findById(cartItemRequest.getProductId());
        Optional<User> user = userRepo.findById(Integer.parseInt(userId));
        if(user.isEmpty()) {
            return false;
        }
        if(product.isEmpty()){
            return false;
        }
        User actualUser = user.get();
        Product actualProduct = product.get();

        if(actualProduct.getStockQuantity() < cartItemRequest.getQuantity()){
            return false;
        }
        if(user.isEmpty()){
            return false;
        }
        CartItem existingCartItem = cartItemRepo.findByUserAndProduct(actualUser,actualProduct);

        if(existingCartItem != null){
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemRequest.getQuantity());
            existingCartItem.setPrice(actualProduct.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRepo.save(existingCartItem);
        }
        else {
            CartItem cartItem = new CartItem();
            cartItem.setUser(actualUser);
            cartItem.setProduct(actualProduct);
            cartItem.setQuantity(cartItemRequest.getQuantity());
            cartItem.setPrice(actualProduct.getPrice().multiply(BigDecimal.valueOf(cartItemRequest.getQuantity())));
            cartItemRepo.save(cartItem);


        }
        return true;


    }

    @Transactional
    public boolean deleteItemFromCart(String userId, int productId) {
        Optional<Product> product = productRepo.findById(productId);
        Optional<User> user = userRepo.findById(Integer.parseInt(userId));
        if(user.isEmpty()) {
            return false;
        }
        if(product.isEmpty()){
            return false;
        }
        User actualUser = user.get();
        Product actualProduct = product.get();


        cartItemRepo.deleteByUserAndProduct(actualUser,actualProduct);
        return true;
    }

    public List<CartItemResponse> getAllProductFromCart(String userId){

        Optional<User> user = userRepo.findById(Integer.parseInt(userId));

        if(user.isPresent()) {

            List<CartItem> cartItems =  cartItemRepo.findByUser(user.get());



            List<CartItemResponse> responseList = cartItems.stream().map(cart -> {
               CartItemResponse cartItemResponse = new CartItemResponse();
               cartItemResponse.setId(cart.getId());
               cartItemResponse.setQuantity(cart.getQuantity());
               cartItemResponse.setPrice(cart.getPrice());

               ProductResponse productResponse = new ProductResponse();
               productResponse.setId(cart.getProduct().getId());
               productResponse.setName(cart.getProduct().getName());
               productResponse.setDescription(cart.getProduct().getDescription());
               productResponse.setPrice(cart.getProduct().getPrice());
               productResponse.setStockQuantity(cart.getProduct().getStockQuantity());
               productResponse.setCategory(cart.getProduct().getCategory());
               productResponse.setImageUrl(cart.getProduct().getImageUrl());
               productResponse.setActive(cart.getProduct().getActive());
               cartItemResponse.setProduct(productResponse);

                UserResponse userResponse = new UserResponse();
                userResponse.setId(cart.getUser().getId());
                userResponse.setFirstName(cart.getUser().getFirstName());
                userResponse.setLastName(cart.getUser().getLastName());
                userResponse.setEmail(cart.getUser().getEmail());
                userResponse.setMobileNo(cart.getUser().getMobileNo());
                userResponse.setRole(cart.getUser().getRole());



                if (cart.getUser().getUserAddress() != null) {
                    AddressResponse addressResponse = new AddressResponse();
                    addressResponse.setCity(cart.getUser().getUserAddress().getCity());
                    addressResponse.setStreet(cart.getUser().getUserAddress().getStreet());
                    addressResponse.setState(cart.getUser().getUserAddress().getState());
                    addressResponse.setPincode(cart.getUser().getUserAddress().getPincode());
                    userResponse.setAddressResponse(addressResponse);
                }
                cartItemResponse.setUser(userResponse);

               return cartItemResponse;

           }).collect(Collectors.toList());

           return responseList;
        }
        return null;
    }

    @Transactional
    public void clearCart(String userId) {
        Optional<User> user = userRepo.findById(Integer.parseInt(userId));

        cartItemRepo.deleteByUser(user.get());

    }
}
