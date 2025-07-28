package com.example.Insta.DTO;

import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class UserDTO {

    private String email;
    private String username;
    private String password;
    private String confirmPassword;


}
