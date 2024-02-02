package com.example.service;

import com.example.model.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.HashSet;
import java.util.Set;

@DataMongoTest
@AutoConfigureDataMongo
public class UnitServiceTest {
    @Autowired
    UnitService unitService;

    @Test
    public void UnitServiceTest_save(){
        //arrange
        User user = new User("John Doe","NORMAL");
        Set<Unit> unitSet = new HashSet<>();
        KeyResult keyResult = new KeyResult("Keys",(short)2,0.2,1.0,0.9,user,"Lorem Ipsum","Ongoing",unitSet);
        Objective objective = new Objective("Objective",(short)4);
        OKRSet[] okrSet = {new OKRSet(objective,keyResult)};
        Unit unit = new Unit(okrSet);

        //act
        Unit savedUnit = unitService.save(unit);

        //assert
        Assertions.assertThat(savedUnit).isNotNull();
        Assertions.assertThat(savedUnit.getUuid()).isEqualByComparingTo(unit.getUuid());
    }

    @Test
    public void UnitServiceTest_findByEmployeeSetContains(){
        //arrange
        User user = new User("John Doe","NORMAL");
        Set<Unit> unitSet = new HashSet<>();
        KeyResult keyResult = new KeyResult("Keys",(short)2,0.2,1.0,0.9,user,"Lorem Ipsum","Ongoing",unitSet);
        Objective objective = new Objective("Objective",(short)4);
        OKRSet[] okrSet = {new OKRSet(objective,keyResult)};
        Unit unit = new Unit(okrSet);
        unitService.save(unit);

        //act
        Unit foundUnit = unitService.findByEmployeeSetContains(user).get();

        //assert
        Assertions.assertThat(foundUnit.getUuid()).isEqualByComparingTo(unit.getUuid());
    }
}
