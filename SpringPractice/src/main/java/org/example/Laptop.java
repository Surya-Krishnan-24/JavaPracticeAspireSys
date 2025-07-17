package org.example;

import org.springframework.stereotype.Component;

@Component
public class Laptop implements Computer {

    private int year;


    public Laptop(){
        System.out.println("Laptop object Created");
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public void compile(){
        System.out.println("Compiling...");
    }
}
