package com.example.SpringBoot.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Employee {

    @Autowired
    Laptop laptop;
    public void code(){

        laptop.compile();
        
    }
}
