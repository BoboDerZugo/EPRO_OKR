package com.example.service;


import com.example.model.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;


@DataMongoTest
@AutoConfigureDataMongo
public class KeyResultServiceTest {

    @Autowired
    KeyResultService keyResultService;

    @Test
    public void KeyResultService_save(){
        //arrange
        KeyResult keyResult = new KeyResult("Keys",(short)2,0.2,1.0,0.9,"Lorem Ipsum","Ongoing");

        KeyResult savedKeyResult = keyResultService.save(keyResult);

        Assertions.assertThat(savedKeyResult).isNotNull();
        Assertions.assertThat(savedKeyResult.getUuid()).isEqualByComparingTo(keyResult.getUuid());
    }

    @Test
    public void KeyResultHistoryService_deleteByUuid(){
        KeyResult keyResult = new KeyResult("Keys",(short)2,0.2,1.0,0.9,"Lorem Ipsum","Ongoing");

        keyResultService.save(keyResult);

        keyResultService.deleteByUuid(keyResult.getUuid());

        Assertions.assertThat(keyResultService.findById(keyResult.getUuid())).isEmpty();

    }
}
