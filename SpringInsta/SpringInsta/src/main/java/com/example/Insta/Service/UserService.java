package com.example.Insta.Service;

import com.example.Insta.DTO.UserDTO;
import com.example.Insta.Exception.UserAlreadyExistException;
import com.example.Insta.Model.User;
import com.example.Insta.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    @Autowired
    UserRepo userRepo;

    public String addUser(UserDTO userDTO) {
        if(!(userDTO.getPassword().equals(userDTO.getConfirmPassword()))){
            throw new RuntimeException("Password does not Match");
        }

        User UniqueUser = userRepo.findByUsername(userDTO.getUsername());
        User UniqueEmail = userRepo.findByEmail(userDTO.getEmail());
        User user = new User();

        if(UniqueUser == null && UniqueEmail == null) {

            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());

            userRepo.save(user);
            User responseUser = userRepo.findByUsername(userDTO.getUsername());
            String response = "User Added Successfully "+responseUser.getUsername();
            return response;
        }
        throw new UserAlreadyExistException("Username or Email is already Taken");

    }

    public String signInUser(UserDTO userDTO) {
        User username = userRepo.findByUsername(userDTO.getUsername());

        if (username == null) {
            username = userRepo.findByEmail(userDTO.getEmail());
        }

        if (username != null && username.getPassword().equals(userDTO.getPassword())) {
            return "Credentials Matched for " + username.getUsername();
        }

        throw new RuntimeException("Invalid credentials.");
    }

}
