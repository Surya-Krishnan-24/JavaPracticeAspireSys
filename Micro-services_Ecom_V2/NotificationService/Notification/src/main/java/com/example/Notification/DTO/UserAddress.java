package com.example.Notification.DTO;


import lombok.Data;

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
