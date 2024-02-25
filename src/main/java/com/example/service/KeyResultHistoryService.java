package com.example.service;

import com.example.model.KeyResultHistory;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface KeyResultHistoryService extends MongoRepository<KeyResultHistory, UUID> {
    /**
     * Deletes KeyResultHistory corresponding to the given ID
     * @param id UUID to be deleted
     * @return Optional containing the deleted KeyResultHistory or NULL
     */
    Optional<KeyResultHistory> deleteByUuid(UUID id);
}
