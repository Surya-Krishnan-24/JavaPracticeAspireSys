package com.example.SpringBoot.service;

import com.example.SpringBoot.model.Laptop;
import com.example.SpringBoot.repo.LaptopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LaptopService {
    @Autowired
    LaptopRepository repo;
    public void addLaptop(Laptop lap) {
        repo.save(lap);
    }

    public boolean isGoodForProgramming(Laptop lap){
        return true;
    }
}
