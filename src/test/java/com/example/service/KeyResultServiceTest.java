package com.example.service;


import com.example.model.*;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.HashSet;
import java.util.Set;

@DataMongoTest
@AutoConfigureDataMongo
public class KeyResultServiceTest {

    @Autowired
    KeyResultService keyResultService;

    @Test
    public void KeyResultService_save(){
        //arrange
        User user = new User("John Doe","CO_ADMIN");
        Set<Unit> unitSet = new HashSet<>();
        KeyResult keyResult = new KeyResult("Keys",(short)2,0.2,1.0,0.9,user,"Lorem Ipsum","Ongoing",unitSet);

        KeyResult savedKeyResult = keyResultService.save(keyResult);

        Assertions.assertThat(savedKeyResult).isNotNull();
        Assertions.assertThat(savedKeyResult.getUuid()).isEqualByComparingTo(keyResult.getUuid());
    }

    @Test
    public void KeyResultService_findOwnerByEquals(){
        User user = new User("John Doe","CO_ADMIN");
        Set<Unit> unitSet = new HashSet<>();
        KeyResult keyResult = new KeyResult("Keys",(short)2,0.2,1.0,0.9,user,"Lorem Ipsum","Ongoing",unitSet);

        KeyResult savedKeyResult = keyResultService.save(keyResult);
        KeyResult filterKeyResult = keyResultService.findByOwnerEquals(user).get();

        Assertions.assertThat(filterKeyResult).isNotNull();
        Assertions.assertThat(filterKeyResult.getUuid()).
                isEqualByComparingTo(keyResult.getUuid());
    }

    @Test
    public void findByContributingUnitsContains(){
        User user = new User("John Doe","CO_ADMIN");
        Set<Unit> unitSet = new HashSet<>();
        KeyResult keyResult = new KeyResult("Keys",(short)2,0.2,1.0,0.9,user,"Lorem Ipsum","Ongoing",unitSet);

        Objective objective = new Objective("Test",(short)3);
        OKRSet[] okrSet = {new OKRSet(objective,keyResult)};
        Unit unit = new Unit(okrSet);
        unitSet.add(unit);

        KeyResult savedKeyResult = keyResultService.save(keyResult);
        KeyResult filterKeyResult = keyResultService.findByContributingUnitsContains(unit).get();

        Assertions.assertThat(filterKeyResult).isNotNull();
        Assertions.assertThat(filterKeyResult.getUuid()).
                isEqualByComparingTo(keyResult.getUuid());
    }
}
