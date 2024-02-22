package com.example.service;

import com.example.model.KeyResultHistory;
//import com.example.model.Unit;
import com.example.model.User;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface KeyResultHistoryService extends MongoRepository<KeyResultHistory, UUID> {
    /**
     * Searches for a KeyResultHistory containing a certain user
     * @param user
     * @return Optional containing a KeyResultHistory containing user or NULL
     */
    Optional<KeyResultHistory> findByOwnerEquals(User user);
    //Optional<KeyResultHistory> findByContributingUnitsContains(Unit unit);
    /**
     * Deletes KeyResultHistory corresponding to the given ID
     * @param id
     * @return Optonal containing the deleted KeyResultHistory or NULL
     */
    Optional<KeyResultHistory> deleteByUuid(UUID id);
}
