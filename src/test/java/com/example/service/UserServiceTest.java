package com.example.service;

import com.example.model.User;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

@DataMongoTest
@AutoConfigureDataMongo
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void UserServiceTest_save(){
        //arrange
        User user = new User("John Doe","NORMAL");

        User savedUser = userService.save(user);

        Assertions.assertThat(savedUser).isEqualTo(user);
    }

    @Test
    public void UserServiceTest_findByRoleEquals(){
        //arrange
        User boss = new User("Bossman", "CO_ADMIN");
        User rightHand = new User("His right hand", "BU_ADMIN");
        User leftHand = new User("His left hand", "BU_ADMIN");
        userService.save(boss);
        userService.save(rightHand);
        userService.save(leftHand);

        //act
        Iterable<User> hands = userService.findByRoleEquals(User.Role.BU_ADMIN);

        Assertions.assertThat(hands).contains(rightHand,leftHand);
        Assertions.assertThat(hands).doesNotContain(boss);
        //assert
    }
}
