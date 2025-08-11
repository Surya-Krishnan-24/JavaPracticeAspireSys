package com.example.UserService.DTO;

import lombok.Data;


@Data
public class AddressResponse {

    private String houseNo;
    private String street;
    private String city;
    private String state;
    private String country;
    private String pincode;
}
