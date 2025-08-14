package com.example.OrderService.DTO;

import com.example.OrderService.Model.UserRole;

import lombok.Data;


@Data
public class UserResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNo;
    private UserRole role;
    private AddressResponse addressResponse;
}
