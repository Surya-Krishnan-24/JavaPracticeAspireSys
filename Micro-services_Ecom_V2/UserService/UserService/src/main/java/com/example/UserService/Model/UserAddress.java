package com.example.UserService.Model;


import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class UserAddress {

    private int id;
    private String houseNo;
    private String street;
    private String city;
    private String state;
    private String country;
    private String pincode;



}
