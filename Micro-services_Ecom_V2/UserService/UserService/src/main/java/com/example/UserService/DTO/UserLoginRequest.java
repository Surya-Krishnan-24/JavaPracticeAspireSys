package com.example.UserService.DTO;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String username;
    private String password;
}
