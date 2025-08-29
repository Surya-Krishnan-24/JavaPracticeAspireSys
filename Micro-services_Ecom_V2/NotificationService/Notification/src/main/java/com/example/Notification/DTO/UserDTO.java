package com.example.Notification.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {

    private String id;
    private String keycloakId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNo;
    private UserRole role;
    private UserAddress userAddress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

