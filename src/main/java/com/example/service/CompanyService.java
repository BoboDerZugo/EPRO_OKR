package com.example.service;

import com.example.model.BusinessUnit;
import com.example.model.Company;
import com.example.model.OKRSet;
import com.example.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyService extends MongoRepository<Company, UUID> {
    Optional<Company> findByBusinessUnitsContains(BusinessUnit businessUnit);
    Optional<Company> findByOkrSetsContains(OKRSet okrSet);
    Optional<Company> findByEmployeeSetContains(User user);
    Optional<Company> updateOne(UUID id, Company company);
    Optional<Company> delete(UUID id);
}
