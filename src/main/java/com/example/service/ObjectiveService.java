package com.example.service;

import com.example.model.Objective;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface ObjectiveService extends MongoRepository<Objective, UUID> {

    /**
     * Finds all Objectives who are being fulfilled more than or equal to
     * a given amount
     *
     * @param target
     * @return Iterable of all Objectives fulfilled more than or equal to target
     */
    Iterable<Objective> findObjectivesByFulfilledGreaterThanEqual(short target);
    /**
     * Finds all Objectives who are being fulfilled less than a given amount
     *
     * @param target
     * @return Iterable of all Objectives fulfilled less than target
     */
    Iterable<Objective> findObjectivesByFulfilledLessThan(short target);
    
    //Delete an objective
    /**
     * Deletes Objective corresponding to the given ID
     * @param id
     * @return Optonal containing the deleted Objective or NULL
     */
    Optional<Objective> deleteByUuid(UUID id);


}
