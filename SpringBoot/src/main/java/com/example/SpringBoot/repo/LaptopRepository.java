package com.example.SpringBoot.repo;

import com.example.SpringBoot.model.Laptop;
import org.springframework.stereotype.Repository;

@Repository
public class LaptopRepository {
    public void save(Laptop lap){
        System.out.println("Data saved");
    }
}
