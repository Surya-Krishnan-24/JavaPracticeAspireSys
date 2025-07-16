package org.example;

import jakarta.persistence.*;

import java.util.List;

@Entity

public class Employee {
    @Id
    private int eID;

    private String eName;

    private String tech;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Laptop> laptops;


    public int geteID() {
        return eID;
    }

    public void seteID(int eID) {
        this.eID = eID;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public String getTech() {
        return tech;
    }

    public void setTech(String tech) {
        this.tech = tech;
    }

    public List<Laptop> getLaptops() {
        return laptops;
    }

    public void setLaptops(List<Laptop> laptops) {
        this.laptops = laptops;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "eID=" + eID +
                ", eName='" + eName + '\'' +
                ", tech='" + tech + '\'' +
                ", laptops=" + laptops +
                '}';
    }
}
