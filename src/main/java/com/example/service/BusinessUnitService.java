package com.example.service;

import com.example.model.BusinessUnit;
import com.example.model.OKRSet;
import com.example.model.Unit;
import com.example.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface BusinessUnitService extends MongoRepository<BusinessUnit, UUID> {


    Optional<BusinessUnit> findByUnitsContains(Unit unit);

    Optional<BusinessUnit> findByOkrSetsContains(OKRSet okrSet);
    Optional<BusinessUnit> findByEmployeeSetContains(User user);

    Optional<BusinessUnit> updateOne(Long id, BusinessUnit businessUnit);

    Optional<BusinessUnit> delete(Long id);


}
