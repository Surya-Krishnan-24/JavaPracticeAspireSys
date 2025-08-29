package com.example.Admin.DTO;

import lombok.Data;


@Data
public class UserRequest {

    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private UserRole userRole = UserRole.ADMIN;
    private String mobileNo;
    private AddressResponse addressResponse;


}
