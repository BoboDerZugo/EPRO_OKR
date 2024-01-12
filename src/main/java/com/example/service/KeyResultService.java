package com.example.service;

import com.example.model.KeyResult;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface KeyResultService extends MongoRepository<KeyResult, UUID> {
}
