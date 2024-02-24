package com.example.service;

import com.example.model.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.HashSet;
import java.util.Set;


/*
    Optional<BusinessUnit> findByUnitsContains(Unit unit);
    Optional<BusinessUnit> findByOkrSetsContains(OKRSet okrSet);
    Optional<BusinessUnit> findByEmployeeSetContains(User user);
 */
@DataMongoTest
@AutoConfigureDataMongo
public class BusinessUnitServiceTest {

    @Autowired
    BusinessUnitService businessUnitService;

    @Test
    public void BusinessUnitServiceTest_save(){
        //arrange
        Set<Unit> unitSet = new HashSet<>();
        Set<OKRSet> okrSets = new HashSet<>();

        BusinessUnit businessUnit = new BusinessUnit(unitSet,okrSets);

        //act
        BusinessUnit savedUnit = businessUnitService.save(businessUnit);

        //assert
        Assertions.assertThat(savedUnit).isNotNull();
        Assertions.assertThat(savedUnit.getUuid()).isEqualByComparingTo(businessUnit.getUuid());
    }

    @Test
    public void BusinessUnitServiceTest_findByUnitsContains(){
        //arrange
        Set<Unit> unitSet = new HashSet<>();
        Set<OKRSet> okrSets = new HashSet<>();

        Unit unit = new Unit();
        unitSet.add(unit);
        BusinessUnit businessUnit = new BusinessUnit(unitSet,okrSets);

        businessUnit.addUnit(unit);
        businessUnitService.save(businessUnit);

        //act
        BusinessUnit savedUnit = businessUnitService.findByUnitsContains(unit).get();
        //assert
        Assertions.assertThat(savedUnit).isNotNull();
        Assertions.assertThat(savedUnit.getUuid()).isEqualByComparingTo(businessUnit.getUuid());
    }
    @Test
    public void BusinessUnitServiceTest_findByOkrSetsContains(){
        //arrange
        Set<Unit> unitSet = new HashSet<>();
        Set<OKRSet> okrSets = new HashSet<>();

        OKRSet okrSet = new OKRSet();
        BusinessUnit businessUnit = new BusinessUnit(unitSet,okrSets);

        businessUnit.addOkrSet(okrSet);
        businessUnitService.save(businessUnit);

        //act
        BusinessUnit savedUnit = businessUnitService.findByOkrSetsContains(okrSet).get();
        //assert
        Assertions.assertThat(savedUnit).isNotNull();
        Assertions.assertThat(savedUnit.getUuid()).isEqualByComparingTo(businessUnit.getUuid());
    }

    @Test
    public void BusinessUnitServiceTest_deleteByUuid(){
        //arrange
        Set<Unit> unitSet = new HashSet<>();
        Set<OKRSet> okrSets = new HashSet<>();

        BusinessUnit businessUnit = new BusinessUnit(unitSet,okrSets);
        businessUnitService.save(businessUnit);
        //act
        businessUnitService.deleteByUuid(businessUnit.getUuid());
        //assert
        Assertions.assertThat(businessUnitService.findById(businessUnit.getUuid())).isEmpty();
    }

}
