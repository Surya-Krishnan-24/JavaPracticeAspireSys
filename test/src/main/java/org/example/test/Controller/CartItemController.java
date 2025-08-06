package org.example.test.Controller;

import org.example.test.DTO.CartItemRequest;
import org.example.test.DTO.CartItemResponse;
import org.example.test.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartItemController {

    @Autowired
    CartService cartService;

    @PostMapping
    public ResponseEntity<Boolean> addToCart(@RequestHeader("X-User-ID") String userId, @RequestBody CartItemRequest cartItemRequest){
        boolean status = cartService.addToCart(userId, cartItemRequest);
        if(!status){
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(true,HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> removeFromCart(@RequestHeader("X-User-ID") String userId,@PathVariable int productId){
        boolean deleted = cartService.deleteItemFromCart(userId,productId);
        return deleted ? new ResponseEntity<>("Deleted",HttpStatus.OK)
                       : new ResponseEntity<>("Product not found for the User",HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getAllProductFromCart(@RequestHeader("X-User-ID") String userId){
        List<CartItemResponse> cartItemResponses = cartService.getAllProductFromCart(userId);

        return cartItemResponses==null ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                                       : new ResponseEntity<>(cartItemResponses,HttpStatus.OK);
    }

}
