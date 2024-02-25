package com.example.service;

import com.example.model.KeyResult;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface KeyResultService extends MongoRepository<KeyResult, UUID> {
    /**
     * Deletes KeyResult corresponding to the given ID
     *
     * @param id UUID to be deleted
     * @return Optional containing the deleted KeyResult or NULL
     */
    Optional<KeyResult> deleteByUuid(UUID id);


}
