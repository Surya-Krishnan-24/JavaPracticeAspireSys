package com.example.Insta.Service;

import com.example.Insta.DTO.UserDTO;
import com.example.Insta.DTO.UserUpdateDTO;
import com.example.Insta.Exception.ImageNotFoundException;
import com.example.Insta.Exception.UserAlreadyExistException;
import com.example.Insta.Exception.UserNotFoundException;
import com.example.Insta.Model.User;
import com.example.Insta.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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


    public User updateProfileData(String username, UserUpdateDTO userUpdateDTO, MultipartFile image) {
        User user = userRepo.findByUsername(username);

        if(user != null) {
            user.setBio(userUpdateDTO.getBio());
            user.setPrivateAccount(userUpdateDTO.isPrivateAccount());
            try {
                user.setProfilePicture(image.getBytes());
            } catch (IOException e) {
                throw new ImageNotFoundException(e.getMessage());
            }
            userRepo.save(user);
            User updateduser = userRepo.findByUsername(username);
            return updateduser;
        }
        throw new UserNotFoundException(username+" not found in the Database to Update");
    }

    public String delete(int id) {
        User find = userRepo.findById(id).orElse(new User());
        if(find.getUserId() != 0) {
            userRepo.deleteById(id);
            return "Deleted";
        }
        throw new UserNotFoundException("User not Found");
    }
}
