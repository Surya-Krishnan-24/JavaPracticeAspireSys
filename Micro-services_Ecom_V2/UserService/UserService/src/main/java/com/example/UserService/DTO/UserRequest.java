package com.example.UserService.DTO;


import lombok.Data;


@Data
public class UserRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String mobileNo;
    private AddressResponse addressResponse;


}
