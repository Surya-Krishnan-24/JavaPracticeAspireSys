package com.example.UserService.Model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class UserAddress {
    private String houseNo;
    private String street;
    private String city;
    private String state;
    private String country;
    private String pincode;
}
