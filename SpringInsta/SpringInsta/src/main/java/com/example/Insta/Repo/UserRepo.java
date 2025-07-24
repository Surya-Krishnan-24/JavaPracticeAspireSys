package com.example.Insta.Repo;

import com.example.Insta.DTO.UserDTO;
import com.example.Insta.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {

    User findByUsername(String Username);

    User findByEmail(String email);
}
