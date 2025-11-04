package com.example.UserService.DTO;


import com.example.UserService.Model.UserRole;
import lombok.Data;


@Data
public class UserRequest {

    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private UserRole userRole = UserRole.USER;
    private String mobileNo;
    private AddressResponse addressResponse;
}
