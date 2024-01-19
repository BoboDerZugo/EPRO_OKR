package com.example.service;

import com.example.model.KeyResult;
import com.example.model.OKRSet;
import com.example.model.Objective;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface OKRSetService extends MongoRepository<OKRSet, UUID> {

    Optional<OKRSet> findByObjective(Objective objective);
    Optional<OKRSet> findByKeyResultsContains(KeyResult keyResult);
}
