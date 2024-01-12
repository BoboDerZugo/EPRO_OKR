package com.example.service;

import com.example.model.BusinessUnit;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface BusinessUnitService extends MongoRepository<BusinessUnit, UUID> {
}
