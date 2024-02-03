package com.example.service;

import com.example.model.KeyResultHistory;
import com.example.model.Unit;
import com.example.model.User;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface KeyResultHistoryService extends MongoRepository<KeyResultHistory, UUID> {
    Optional<KeyResultHistory> findByOwnerEquals(User user);
    Optional<KeyResultHistory> findByContributingUnitsContains(Unit unit);
    Optional<KeyResultHistory> deleteByUuid(UUID id);
}
