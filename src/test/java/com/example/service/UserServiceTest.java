package com.example.service;

import com.example.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

@DataMongoTest
@AutoConfigureDataMongo
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void UserServiceTest_save() {
        //arrange
        User user = new User("John Doe", "password", "NORMAL");

        User savedUser = userService.save(user);

        Assertions.assertThat(savedUser).isEqualTo(user);
    }

    @Test
    public void UserServiceTest_findByRoleEquals() {
        //arrange
        User boss = new User("Bossman", "password", "CO_ADMIN");
        User rightHand = new User("His right hand", "password", "BU_ADMIN");
        User leftHand = new User("His left hand", "password", "BU_ADMIN");
        userService.save(boss);
        userService.save(rightHand);
        userService.save(leftHand);

        //act
        Iterable<User> hands = userService.findByRoleEquals(User.Role.BU_ADMIN);

        Assertions.assertThat(hands).contains(rightHand, leftHand);
        Assertions.assertThat(hands).doesNotContain(boss);
        //assert
    }

    @Test
    public void UserService_deleteByUuid() {
        User user = new User("User","password", "NORMAL");
        userService.save(user);

        userService.deleteByUuid(user.getUuid());

        Assertions.assertThat(userService.findById(user.getUuid())).isEmpty();
    }
}
