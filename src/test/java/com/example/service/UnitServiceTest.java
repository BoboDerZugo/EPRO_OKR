package com.example.service;

import com.example.model.Unit;
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
    public void UnitServiceTest_save() {
        //arrange
        User user = new User("John Doe", "password", "NORMAL");
        Set<Unit> unitSet = new HashSet<>();
        KeyResult keyResult = new KeyResult("Keys", (short) 2, 0.2, 1.0, 0.9, "Lorem Ipsum", "Ongoing");
        Objective objective = new Objective("Objective", (short) 4);
        OKRSet[] okrSet = {new OKRSet(objective, keyResult)};
        Set<User> userSet = new HashSet<>();
        Unit unit = new Unit(userSet);

        //act
        Unit savedUnit = unitService.save(unit);

        //assert
        Assertions.assertThat(savedUnit).isNotNull();
        Assertions.assertThat(savedUnit.getUuid()).isEqualByComparingTo(unit.getUuid());
    }

    @Test
    public void UnitService_deleteByUuid() {
        Unit unit = new Unit();

        unitService.save(unit);

        unitService.deleteByUuid(unit.getUuid());

        Assertions.assertThat(unitService.findById(unit.getUuid())).isEmpty();
    }
}
