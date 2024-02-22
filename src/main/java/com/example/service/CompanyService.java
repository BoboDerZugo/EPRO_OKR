package com.example.service;

import com.example.model.BusinessUnit;
import com.example.model.Company;
import com.example.model.OKRSet;
//import com.example.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyService extends MongoRepository<Company, UUID> {
    /**
     * Searches for a Company containing a certain BusinessUnit
     *
     * @param businessUnit
     * @return Optional containing a Company containing businessUnit or NULL
     */
    Optional<Company> findByBusinessUnitsContains(BusinessUnit businessUnit);

    /**
     * Searches for a Company containing a certain OKRSet
     *
     * @param okrSet
     * @return Optional containing a Company containing okrSet or NULL
     */
    Optional<Company> findByOkrSetsContains(OKRSet okrSet);

    /**
     * Deletes Company corresponding to the given ID
     * @param id
     * @return Optonal containing the deleted Company or NULL
     */
    Optional<Company> deleteByUuid(UUID id);
}
