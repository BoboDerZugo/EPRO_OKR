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
    public void KeyResultHistory_save(){
        //arrange
        KeyResultHistory keyResultHistory = new KeyResultHistory("Keys",(short)2,0.2,1.0,0.9,"Lorem Ipsum","Ongoing");

        KeyResultHistory savedKeyResult = keyResultHistoryService.save(keyResultHistory);

        Assertions.assertThat(savedKeyResult).isNotNull();
        Assertions.assertThat(savedKeyResult).isEqualTo(savedKeyResult);
    }


    @Test
    public void KeyResultHistoryService_deleteByUuid(){

        KeyResultHistory keyResult = new KeyResultHistory("Keys",(short)2,0.2,1.0,0.9,"Lorem Ipsum","Ongoing");

        keyResultHistoryService.save(keyResult);

        keyResultHistoryService.deleteByUuid(keyResult.getUuid());

        Assertions.assertThat(keyResultHistoryService.findById(keyResult.getUuid())).isEmpty();

    }
}
