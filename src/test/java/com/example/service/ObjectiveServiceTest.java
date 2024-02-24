package com.example.service;

import com.example.model.Objective;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

@DataMongoTest
@AutoConfigureDataMongo
public class ObjectiveServiceTest {

    @Autowired
    private ObjectiveService objectiveService;

    @Test
    public void ObjectiveServiceTest_save(){

        //arrange
        Objective objective = new Objective("First Objective",(short) 3);

        //act
        Objective savedObjective = objectiveService.save(objective);
        //assert
        Assertions.assertThat(savedObjective).isNotNull();
        Assertions.assertThat(savedObjective.getUuid()).isEqualByComparingTo(objective.getUuid());
    }

    @Test
    public void ObjectiveService_findObjectivesByFulfilledGreaterThanEqual(){

        //arrange
        Objective objective1 = new Objective("First Objective",(short) 1);
        Objective objective2 = new Objective("First Objective",(short) 2);
        Objective objective3 = new Objective("First Objective",(short) 3);
        Objective objective4 = new Objective("First Objective",(short) 4);

        //arrange
        objectiveService.save(objective1);
        objectiveService.save(objective2);
        objectiveService.save(objective3);
        objectiveService.save(objective4);

        Iterable<Objective> objectivesFiltered = objectiveService.findObjectivesByFulfilledGreaterThanEqual((short)2);

        //assert

        Assertions.assertThat(objectivesFiltered).contains(objective2,objective3,objective4);
        Assertions.assertThat(objectivesFiltered).doesNotContain(objective1);
    }

    @Test
    public void ObjectiveService_findObjectivesByFulfilledLessThan(){
        //arrange
        Objective objective1 = new Objective("First Objective",(short) 1);
        Objective objective2 = new Objective("First Objective",(short) 2);
        Objective objective3 = new Objective("First Objective",(short) 3);
        Objective objective4 = new Objective("First Objective",(short) 4);

        //arrange
        objectiveService.save(objective1);
        objectiveService.save(objective2);
        objectiveService.save(objective3);
        objectiveService.save(objective4);

        Iterable<Objective> objectivesFiltered = objectiveService.findObjectivesByFulfilledLessThan((short)2);

        //assert

        Assertions.assertThat(objectivesFiltered).doesNotContain(objective2,objective3,objective4);
        Assertions.assertThat(objectivesFiltered).contains(objective1);
    }

    @Test
    public void ObjectiveService_deleteByUuid(){

        Objective objective = new Objective("Test Objective",(short) 1);

        objectiveService.save(objective);

        objectiveService.deleteByUuid(objective.getUuid());

        Assertions.assertThat(objectiveService.findById(objective.getUuid())).isEmpty();
    }
}
