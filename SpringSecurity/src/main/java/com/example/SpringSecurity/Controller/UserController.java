package com.example.SpringSecurity.Controller;


import com.example.SpringSecurity.Model.User;
import com.example.SpringSecurity.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("register")
    public User register(@RequestBody User user){
        return userService.saveUser(user);

    }

    @GetMapping("get/{id}")
    public User get(@PathVariable int id){
        User user = userService.get(id);
        return user;
    }
}
