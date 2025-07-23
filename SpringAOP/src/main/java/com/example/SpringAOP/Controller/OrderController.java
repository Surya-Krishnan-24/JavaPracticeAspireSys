package com.example.SpringAOP.Controller;

import com.example.SpringAOP.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/order/{item}")
    public void placeOrder(String item){
        orderService.placeOrder(item);
    }
}
