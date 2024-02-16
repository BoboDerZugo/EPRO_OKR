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
        User user = new User("John Doe","NORMAL");
        Set<Unit> unitSet = new HashSet<>();
        KeyResult keyResult = new KeyResult("Keys",(short)2,0.2,1.0,0.9,user,"Lorem Ipsum","Ongoing");
        Objective objective = new Objective("Objective",(short)4);
        OKRSet[] okrSet = {new OKRSet(objective,keyResult)};

        BusinessUnit businessUnit = new BusinessUnit(okrSet);

        //act
        BusinessUnit savedUnit = businessUnitService.save(businessUnit);

        //assert
        Assertions.assertThat(savedUnit).isNotNull();
        Assertions.assertThat(savedUnit.getUuid()).isEqualByComparingTo(businessUnit.getUuid());
    }

    @Test
    public void BusinessUnitServiceTest_findByUnitsContains(){
        //arrange
        User user = new User("John Doe","NORMAL");
        Set<Unit> unitSet = new HashSet<>();
        KeyResult keyResult = new KeyResult("Keys",(short)2,0.2,1.0,0.9,user,"Lorem Ipsum","Ongoing");
        Objective objective = new Objective("Objective",(short)4);
        OKRSet[] okrSet = {new OKRSet(objective,keyResult)};
        Set<User> employees = Set.of(user);
        Unit unit = new Unit(employees);


        BusinessUnit businessUnit = new BusinessUnit(okrSet);
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
        User user = new User("John Doe","NORMAL");
        Set<Unit> unitSet = new HashSet<>();
        KeyResult keyResult = new KeyResult("Keys",(short)2,0.2,1.0,0.9,user,"Lorem Ipsum","Ongoing");
        Objective objective = new Objective("Objective",(short)4);
        OKRSet[] okrSet = {new OKRSet(objective,keyResult)};

        BusinessUnit businessUnit = new BusinessUnit(okrSet);
        businessUnitService.save(businessUnit);

        //act
        BusinessUnit savedUnit = businessUnitService.findByOkrSetsContains(okrSet[0]).get();
        //assert
        Assertions.assertThat(savedUnit).isNotNull();
        Assertions.assertThat(savedUnit.getUuid()).isEqualByComparingTo(businessUnit.getUuid());
    }
/*    @Test
    public void BusinessUnitServiceTest_findByEmployeeSetContains(){
        //arrange
        User user = new User("John Doe","NORMAL");
        Set<Unit> unitSet = new HashSet<>();
        KeyResult keyResult = new KeyResult("Keys",(short)2,0.2,1.0,0.9,user,"Lorem Ipsum","Ongoing",unitSet);
        Objective objective = new Objective("Objective",(short)4);
        OKRSet[] okrSet = {new OKRSet(objective,keyResult)};


        BusinessUnit businessUnit = new BusinessUnit(okrSet);
        businessUnit.addEmployee(user);
        businessUnitService.save(businessUnit);

        //act
        BusinessUnit savedUnit = businessUnitService.findByEmployeeSetContains(user).get();
        //assert
        Assertions.assertThat(savedUnit).isNotNull();
        Assertions.assertThat(savedUnit.getUuid()).isEqualByComparingTo(businessUnit.getUuid());
    }
    */

    @Test
    public void BusinessUnitServiceTest_deleteByUuid(){
        //arrange
        User user = new User("John Doe","NORMAL");
        Set<Unit> unitSet = new HashSet<>();
        Objective objective = new Objective("Objective",(short)4);
        KeyResult keyResult = new KeyResult("Keys",(short)2,0.2,1.0,0.9,user,"Lorem Ipsum","Ongoing");
        OKRSet okrSet = new OKRSet(objective,keyResult);
        keyResult.setOkrSet(okrSet);

        BusinessUnit businessUnit = new BusinessUnit(new OKRSet[]{okrSet});
        businessUnitService.save(businessUnit);
        //act
        businessUnitService.deleteByUuid(businessUnit.getUuid());
        //assert
        Assertions.assertThat(businessUnitService.findById(businessUnit.getUuid())).isEmpty();
    }

}
