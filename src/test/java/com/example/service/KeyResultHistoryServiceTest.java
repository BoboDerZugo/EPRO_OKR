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
public class KeyResultHistoryServiceTest {
    @Autowired
    KeyResultHistoryService keyResultHistoryService;

    @Test
    public void KeyResultService_save(){
        //arrange
        User user = new User("John Doe","CO_ADMIN");
        Set<Unit> unitSet = new HashSet<>();
        KeyResultHistory keyResultHistory = new KeyResultHistory("Keys",(short)2,0.2,1.0,0.9,user,"Lorem Ipsum","Ongoing");

        KeyResultHistory savedKeyResult = keyResultHistoryService.save(keyResultHistory);

        Assertions.assertThat(savedKeyResult).isNotNull();
        Assertions.assertThat(savedKeyResult).isEqualTo(savedKeyResult);
    }

    @Test
    public void KeyResultService_findOwnerByEquals(){
        User user = new User("John Doe","CO_ADMIN");
        Set<Unit> unitSet = new HashSet<>();
        KeyResultHistory keyResult = new KeyResultHistory("Keys",(short)2,0.2,1.0,0.9,user,"Lorem Ipsum","Ongoing");

        KeyResultHistory savedKeyResult = keyResultHistoryService.save(keyResult);
        KeyResultHistory filterKeyResult = keyResultHistoryService.findByOwnerEquals(user).get();

        Assertions.assertThat(filterKeyResult).isNotNull();
        Assertions.assertThat(filterKeyResult).isEqualTo(savedKeyResult);
    }

    @Test
    public void KeyResultHistoryService_deleteByUuid(){
        User user = new User("John Doe","CO_ADMIN");
        Set<Unit> unitSet = new HashSet<>();
        KeyResultHistory keyResult = new KeyResultHistory("Keys",(short)2,0.2,1.0,0.9,user,"Lorem Ipsum","Ongoing");

        keyResultHistoryService.save(keyResult);

        keyResultHistoryService.deleteByUuid(keyResult.getUuid());

        Assertions.assertThat(keyResultHistoryService.findById(keyResult.getUuid())).isEmpty();

    }

    /*
    @Test
    public void findByContributingUnitsContains(){
        User user = new User("John Doe","CO_ADMIN");
        Set<Unit> unitSet = new HashSet<>();
        KeyResultHistory keyResultHistory = new KeyResultHistory("Keys",(short)2,0.2,1.0,0.9,user,"Lorem Ipsum","Ongoing",unitSet);
        KeyResult keyResult = new KeyResult("Keys",(short)2,0.2,1.0,0.9,user,"Lorem Ipsum","Ongoing",unitSet);

        Objective objective = new Objective("Test",(short)3);
        OKRSet[] okrSet = {new OKRSet(objective,keyResult)};
        Unit unit = new Unit(okrSet);
        unitSet.add(unit);

        KeyResultHistory savedKeyResult = keyResultHistoryService.save(keyResultHistory);
        KeyResultHistory filterKeyResult = keyResultHistoryService.findByContributingUnitsContains(unit).get();

        Assertions.assertThat(filterKeyResult).isNotNull();
        Assertions.assertThat(filterKeyResult).isEqualTo(savedKeyResult);
    }*/
}
