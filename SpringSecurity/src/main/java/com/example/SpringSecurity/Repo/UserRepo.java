package com.example.SpringSecurity.Repo;

import com.example.SpringSecurity.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {
    User findByUsername(String username);

}
