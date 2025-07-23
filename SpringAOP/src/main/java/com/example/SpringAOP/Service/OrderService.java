package com.example.SpringAOP.Service;

import org.springframework.stereotype.Service;

@Service
public class OrderService {
    public void placeOrder(String item){
        System.out.println("Order Placed for: "+ item);
    }
}
