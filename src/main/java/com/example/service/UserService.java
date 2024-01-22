package com.example.service;

import com.example.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserService extends MongoRepository<User, UUID> {

    Iterable<User> findByRoleEquals(User.Role role);

    Optional<User> updateOne(UUID id, User user);

    Optional<User> delete(UUID id);
}
