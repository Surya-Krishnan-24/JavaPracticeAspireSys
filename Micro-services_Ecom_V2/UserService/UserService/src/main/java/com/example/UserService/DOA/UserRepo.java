package com.example.UserService.DOA;

import com.example.UserService.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepo extends MongoRepository<User,String> {
    User findByKeycloakId(String keycloakId);

    User findByUsername(String name);
}
