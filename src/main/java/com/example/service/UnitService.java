package com.example.service;

import com.example.model.BusinessUnit;
import com.example.model.OKRSet;
import com.example.model.Unit;
import com.example.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface UnitService extends MongoRepository<Unit, UUID> {
    Optional<Unit> findByEmployeeSetContains(User user);
    Optional<Unit> findByOkrSetsContains(OKRSet okrSet);
    Optional<Unit> updateOne(UUID id, Unit unit);
    Optional<Unit> delete(UUID id);
}
