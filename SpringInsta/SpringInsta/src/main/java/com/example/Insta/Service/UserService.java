package com.example.Insta.Service;

import com.example.Insta.DTO.UserDTO;
import com.example.Insta.DTO.UserUpdateDTO;
import com.example.Insta.Exception.ImageNotFoundException;
import com.example.Insta.Exception.UserAlreadyExistException;
import com.example.Insta.Exception.UserNotFoundException;
import com.example.Insta.Model.User;
import com.example.Insta.Repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UserService {


    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public String addUser(UserDTO userDTO) {
        if(!(userDTO.getPassword().equals(userDTO.getConfirmPassword()))){
            throw new RuntimeException("Password does not Match");
        }

        User UniqueUser = userRepo.findByUsername(userDTO.getUsername()).orElse(null);
        User UniqueEmail = userRepo.findByEmail(userDTO.getEmail());
        User user = new User();

        if(UniqueUser == null && UniqueEmail == null) {

            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            user.setPrivateAccount(false);
            user.setRole("USER");

            userRepo.save(user);
            User responseUser = userRepo.findByUsername(userDTO.getUsername()).orElseThrow(() -> new UserNotFoundException("User not saved in the Database"));
            String response = "User Added Successfully "+responseUser.getUsername();
            return response;
        }
        throw new UserAlreadyExistException("Username or Email is already Taken");

    }

    @Transactional
    public String signInUser(UserDTO userDTO) {
        User user = userRepo.findByUsername(userDTO.getUsername()).orElse(null);
        System.out.println("hello");

        if (user == null && userDTO.getEmail() != null) {
            user = userRepo.findByEmail(userDTO.getEmail());
        }

        if (user != null && passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            return "Credentials Matched for " + user.getUsername();
        }
        else {
            System.out.println("invalid");
        }

        throw new RuntimeException("Invalid credentials.");
    }



    @Transactional
    public User updateProfileData(String username, UserUpdateDTO userUpdateDTO, MultipartFile image) {
        User user = userRepo.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found in Database"));

        if(user != null) {
            user.setBio(userUpdateDTO.getBio());
            user.setPrivateAccount(userUpdateDTO.isPrivateAccount());

            String currentpass = user.getPassword();
            user.setPassword(currentpass);


            if(!image.isEmpty()) {
                try {
                    user.setProfilePicture(image.getBytes());
                } catch (IOException e) {
                    throw new ImageNotFoundException(e.getMessage());
                }
            }
            System.out.println("Password before save: " + user.getPassword());
            userRepo.save(user);
            User updateduser = userRepo.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not Saved in Database"));
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
