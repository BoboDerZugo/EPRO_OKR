package com.example.service;

import com.example.model.KeyResult;
//import com.example.model.Unit;
import com.example.model.User;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface KeyResultService extends MongoRepository<KeyResult, UUID> {
    /**
     * Searches for a KeyResult containing a certain user
     * @param user
     * @return Optional containing a KeyResult containing user or NULL
     */
    Optional<KeyResult> findByOwnerEquals(User user);
    //Optional<KeyResult> findByContributingUnitsContains(Unit unit);
    /**
     * Deletes KeyResult corresponding to the given ID
     * @param id
     * @return Optonal containing the deleted KeyResult or NULL
     */
    Optional<KeyResult> deleteByUuid(UUID id);


}
