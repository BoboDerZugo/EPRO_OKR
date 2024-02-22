package com.example.service;

import com.example.model.BusinessUnit;
import com.example.model.OKRSet;
import com.example.model.Unit;
//import com.example.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface BusinessUnitService extends MongoRepository<BusinessUnit, UUID> {


    /**
     * Searches for a BusinessUnit containing a certain Unit
     *
     * @param unit
     * @return Optional containing a BusinessUnit containing unit or NULL
     */
    Optional<BusinessUnit> findByUnitsContains(Unit unit);

    /**
     * Searches for a BusinessUnit containing a certain OKRSet
     * @param okrSet
     * @return Optional containing a BusinessUnit containing okrSet or NULL
     */
    Optional<BusinessUnit> findByOkrSetsContains(OKRSet okrSet);
    //Optional<BusinessUnit> findByEmployeeSetContains(User user);

    // Optional<BusinessUnit> findByUuidAndUpdate(UUID id, BusinessUnit businessUnit);

    /**
     * Deletes BusinessUnit corresponding to the given ID
     * @param id
     * @return Optonal containing the deleted BusinessUnit or NULL
     */
    Optional<BusinessUnit> deleteByUuid(UUID id);


}
