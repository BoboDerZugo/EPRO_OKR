package com.example.service;


import com.example.model.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

@DataMongoTest
@AutoConfigureDataMongo
public class OKRSetServiceTest {

    @Autowired
    OKRSetService okrSetService;

    @Test
    public void OKRSetService_save(){
        //arrange
        KeyResult keyResult = new KeyResult("Keys",(short)2,0.2,1.0,0.9,"Lorem Ipsum","Ongoing");
        Objective objective = new Objective("test", (short) 4);
        OKRSet okrSet = new OKRSet(objective,keyResult);
        //act
        OKRSet savedOKRSet = okrSetService.save(okrSet);
        //assert
        Assertions.assertThat(savedOKRSet).isNotNull();
        Assertions.assertThat(savedOKRSet.getUuid()).isEqualByComparingTo(keyResult.getUuid());
    }

    @Test
    public void OKRSetService_findObjective(){
        //arrange

        KeyResult keyResult = new KeyResult("Keys",(short)2,0.2,1.0,0.9,"Lorem Ipsum","Ongoing");
        Objective objective = new Objective("test", (short) 4);
        OKRSet okrSet = new OKRSet(objective,keyResult);

        OKRSet savedOKRSet = okrSetService.save(okrSet);
        //act
        OKRSet foundOKRSet = okrSetService.findByObjective(objective).get();

        //assert

        Assertions.assertThat(foundOKRSet.getUuid()).isEqualByComparingTo(savedOKRSet.getUuid());
    }

    @Test
    public void OKRSetService_findByKeyResultsContains(){
        //arrange
        KeyResult keyResult = new KeyResult("Keys",(short)2,0.2,1.0,0.9,"Lorem Ipsum","Ongoing");
        Objective objective = new Objective("test", (short) 4);
        OKRSet okrSet = new OKRSet(objective,keyResult);

        OKRSet savedOKRSet = okrSetService.save(okrSet);
        //act
        OKRSet foundOKRSet = okrSetService.findByKeyResultsContains(keyResult).get();

        //assert

        Assertions.assertThat(foundOKRSet.getUuid()).isEqualByComparingTo(savedOKRSet.getUuid());
    }

    @Test
    public void OKRSetService_deleteByUuid(){
        OKRSet okrSet = new OKRSet();

        okrSetService.save(okrSet);

        okrSetService.deleteByUuid(okrSet.getUuid());

        Assertions.assertThat(okrSetService.deleteByUuid(okrSet.getUuid())).isEmpty();
    }
}
