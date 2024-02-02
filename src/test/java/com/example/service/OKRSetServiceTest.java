package com.example.service;


import com.example.model.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.HashSet;
import java.util.Set;

@DataMongoTest
@AutoConfigureDataMongo
public class OKRSetServiceTest {

    @Autowired
    OKRSetService okrSetService;

    @Test
    public void KeyResultService_save(){
        //arrange
        User user = new User("John Doe","CO_ADMIN");
        Set<Unit> unitSet = new HashSet<>();
        KeyResult keyResult = new KeyResult("Keys",(short)2,0.2,1.0,0.9,user,"Lorem Ipsum","Ongoing",unitSet);
        Objective objective = new Objective("test", (short) 4);
        OKRSet okrSet = new OKRSet(objective,keyResult);
        //act
        OKRSet savedOKRSet = okrSetService.save(okrSet);
        //assert
        Assertions.assertThat(savedOKRSet).isNotNull();
        Assertions.assertThat(savedOKRSet.getUuid()).isEqualByComparingTo(keyResult.getUuid());
    }

    @Test
    public void KeyResult_findObjective(){
        //arrange
        User user = new User("John Doe","CO_ADMIN");
        Set<Unit> unitSet = new HashSet<>();
        KeyResult keyResult = new KeyResult("Keys",(short)2,0.2,1.0,0.9,user,"Lorem Ipsum","Ongoing",unitSet);
        Objective objective = new Objective("test", (short) 4);
        OKRSet okrSet = new OKRSet(objective,keyResult);

        OKRSet savedOKRSet = okrSetService.save(okrSet);
        //act
        OKRSet foundOKRSet = okrSetService.findByObjective(objective).get();

        //assert

        Assertions.assertThat(foundOKRSet.getUuid()).isEqualByComparingTo(savedOKRSet.getUuid());
    }

    @Test
    public void KeyResult_findByKeyResultsContains(){
        //arrange
        User user = new User("John Doe","CO_ADMIN");
        Set<Unit> unitSet = new HashSet<>();
        KeyResult keyResult = new KeyResult("Keys",(short)2,0.2,1.0,0.9,user,"Lorem Ipsum","Ongoing",unitSet);
        Objective objective = new Objective("test", (short) 4);
        OKRSet okrSet = new OKRSet(objective,keyResult);

        OKRSet savedOKRSet = okrSetService.save(okrSet);
        //act
        OKRSet foundOKRSet = okrSetService.findByKeyResultsContains(keyResult).get();

        //assert

        Assertions.assertThat(foundOKRSet.getUuid()).isEqualByComparingTo(savedOKRSet.getUuid());
    }
}
