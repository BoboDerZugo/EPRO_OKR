package com.example.service;

import com.example.model.KeyResult;
import com.example.model.Unit;
import com.example.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface KeyResultService extends MongoRepository<KeyResult, UUID> {
    Optional<KeyResult> findByOwnerEquals(User user);
    Optional<KeyResult> findByContributingUnitsContains(Unit unit);


}
