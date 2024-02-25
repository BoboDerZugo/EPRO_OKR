package com.example.service;

import com.example.model.BusinessUnit;
import com.example.model.OKRSet;
import com.example.model.Unit;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface BusinessUnitService extends MongoRepository<BusinessUnit, UUID> {


    /**
     * Searches for a BusinessUnit containing a certain Unit
     *
     * @param unit Unit filtered for
     * @return Optional containing a BusinessUnit containing unit or NULL
     */
    Optional<BusinessUnit> findByUnitsContains(Unit unit);

    /**
     * Searches for a BusinessUnit containing a certain OKRSet
     *
     * @param okrSet OKRSet filtered for
     * @return Optional containing a BusinessUnit containing okrSet or NULL
     */
    Optional<BusinessUnit> findByOkrSetsContains(OKRSet okrSet);

    /**
     * Deletes BusinessUnit corresponding to the given ID
     *
     * @param id UUID to be deleted
     * @return Optional containing the deleted BusinessUnit or NULL
     */
    Optional<BusinessUnit> deleteByUuid(UUID id);


}
