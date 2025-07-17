package com.example.SpringJDBC.model;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Student {
    private int RollNo;
    private String Name;
    private int Marks;

    public int getRollNo() {
        return RollNo;
    }

    public void setRollNo(int rollNo) {
        RollNo = rollNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getMarks() {
        return Marks;
    }

    public void setMarks(int marks) {
        Marks = marks;
    }

    @Override
    public String toString() {
        return "Student{" +
                "RollNo=" + RollNo +
                ", Name='" + Name + '\'' +
                ", Marks=" + Marks +
                '}';
    }
}
