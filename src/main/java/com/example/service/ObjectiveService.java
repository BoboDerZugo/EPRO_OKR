package com.example.service;

import com.example.model.Objective;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface ObjectiveService extends MongoRepository<Objective, UUID> {

    Iterable<Objective> findObjectivesByFulfilledGreaterThanEqual(short target);
    Iterable<Objective> findObjectivesByFulfilledLessThan(short target);
    
    //Update an objective
    Optional<Objective> updateOne(UUID id, Objective objective);
    //Delete an objective
    Optional<Objective> findByIdAndDelete(UUID id);


}
