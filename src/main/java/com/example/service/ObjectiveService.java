package com.example.service;

import com.example.model.Objective;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface ObjectiveService extends MongoRepository<Objective, UUID> {

    Iterable<Objective> findObjectivesByFulfilledGreaterThanEqual(short target);
}
