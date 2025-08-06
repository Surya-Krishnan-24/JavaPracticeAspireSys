package org.example.test.DTO;


import lombok.Data;
import org.example.test.Model.UserAddress;


@Data
public class UserRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String mobileNo;
    private AddressResponse addressResponse;


}
