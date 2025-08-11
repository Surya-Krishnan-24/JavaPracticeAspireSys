package com.example.UserService.Model;

import lombok.Data;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDateTime;

@Data
@Document(collection = "users")
public class User {

    @Id

    private String id;
    private String firstName;
    private String lastName;

    @Indexed(unique = true)
    private String email;
    private String mobileNo;
    private UserRole role = UserRole.USER;

    private UserAddress userAddress;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;


}
