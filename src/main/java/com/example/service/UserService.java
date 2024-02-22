package com.example.service;

import com.example.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserService extends MongoRepository<User, UUID> {

    /**
     * Searches for a User assigned as a certain role
     * @param role
     * @return Optional containing a User containing user or NULL
     */
    Iterable<User> findByRoleEquals(User.Role role);
    /**
     * Deletes User corresponding to the given ID
     * @param id
     * @return Optonal containing the deleted User or NULL
     */
    Optional<User> deleteByUuid(UUID id);
}
