package com.example.service;

import com.example.model.KeyResult;
import com.example.model.OKRSet;
import com.example.model.Objective;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface OKRSetService extends MongoRepository<OKRSet, UUID> {

    /**
     * Searches for a OKRSet containing a certain objective
     * @param objective
     * @return Optional containing a OKRSet containing objective or NULL
     */
    Optional<OKRSet> findByObjective(Objective objective);
    /**
     * Searches for a OKRSet containing a certain keyResult
     * @param keyResult
     * @return Optional containing a OKRSet containing keyResult or NULL
     */
    Optional<OKRSet> findByKeyResultsContains(KeyResult keyResult);

    /**
     * Deletes OKRSet corresponding to the given ID
     * @param id
     * @return Optonal containing the deleted KeyResult or NULL
     */
    Optional<OKRSet> deleteByUuid(UUID id);
}
