package org.example.test.DTO;

import lombok.Data;
import org.example.test.Model.UserRole;


@Data
public class UserResponse {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNo;
    private UserRole role;
    private AddressResponse addressResponse;
}
