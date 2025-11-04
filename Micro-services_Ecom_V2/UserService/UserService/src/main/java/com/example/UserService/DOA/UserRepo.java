package com.example.UserService.DOA;

import com.example.UserService.Model.User;

import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepo extends JpaRepository<User,Long> {
    User findByKeycloakId(String keycloakId);

    User findByUsername(String name);
}
