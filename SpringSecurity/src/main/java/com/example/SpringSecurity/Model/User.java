package com.example.SpringSecurity.Model;

import jakarta.persistence.*;

import lombok.Data;


@Data
@Entity
@Table(name = "users_list")
public class User {
    @Id
    private int id;
    private String username;
    private String password;
}
