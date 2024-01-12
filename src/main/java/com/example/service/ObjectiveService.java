package com.example.service;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface ObjectiveService extends MongoRepository<Object, UUID> {
}
