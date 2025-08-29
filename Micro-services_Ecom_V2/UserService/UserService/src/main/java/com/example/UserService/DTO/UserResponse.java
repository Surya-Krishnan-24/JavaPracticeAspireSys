package com.example.UserService.DTO;

import lombok.Data;
import com.example.UserService.Model.UserRole;


@Data
public class UserResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String mobileNo;
    private UserRole role;
    private AddressResponse addressResponse;
}
