package org.example;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

@Component
public class Employee {

    private int age;

    @Autowired
    private Computer lap;

//    public Employee(int age, Laptop lap) {
//        this.age = age;
//        this.lap = lap;
//    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Computer getLap() {
        return lap;
    }

    public void setLap(Computer lap) {
        this.lap = lap;
    }

    public void code(){
        System.out.println("coding..");
        lap.compile();

    }
}
