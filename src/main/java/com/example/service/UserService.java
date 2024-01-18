package com.example.service;

import com.example.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface UserService extends MongoRepository<User, UUID> {

    Iterable<User> findByRoleEquals(User.Role role);
}
