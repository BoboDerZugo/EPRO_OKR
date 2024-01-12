package com.example.service;

import com.example.model.OKRSet;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface OKRSetService extends MongoRepository<OKRSet, UUID> {
}
