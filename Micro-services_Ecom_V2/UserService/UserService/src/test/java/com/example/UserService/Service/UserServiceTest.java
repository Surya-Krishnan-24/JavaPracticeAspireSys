package com.example.UserService.Service;

import com.example.UserService.DOA.UserRepo;
import com.example.UserService.DTO.UserResponse;
import com.example.UserService.Model.User;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepo userRepo;
    @InjectMocks
    UserService userService;

    @Test
    void fetchAllUsers() {

        User user1 = new User();

        User user2 = new User();
        List<User> users = Arrays.asList(user1,user2);

        Mockito.when(userRepo.findAll()).thenReturn(users);
        List<UserResponse> ur = userService.fetchAllUsers();


    }

    @Test
    void createUser() {
    }

    @Test
    void getUserById() {
    }

    @Test
    void getUserBySellerName() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void loginUser() {
    }

    @Test
    void getUserIdByKeyCloak() {
    }

    @Test
    void getUserFullNameById() {
    }

    @Test
    void getUserAddressById() {
    }

    @Test
    void getUserEmail() {
    }
}