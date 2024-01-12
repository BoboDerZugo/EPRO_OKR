package com.example.service;

import com.example.model.Unit;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface UnitService extends MongoRepository<Unit, UUID> {
}
