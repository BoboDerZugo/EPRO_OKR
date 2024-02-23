package com.example.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.model.User;
import com.example.service.UserService;

@SpringBootTest
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user;

    private List<User> users;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setUuid(UUID.randomUUID());

        users = new ArrayList<>();
        users.add(user);
    }

    // Test for the getAllUsers method
    // Should return 200 OK and a list of users
    @Test
    public void testGetAllUsers() {
        // Arrange
        when(userService.findAll()).thenReturn(users);

        // Act
        ResponseEntity<List<User>> response = userController.getAllUsers();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(users);
    }

    // Test for the getUser method
    // Should return 200 OK and a user
    @Test
    public void testGetUser() {
        // Arrange
        when(userService.findById(user.getUuid())).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<User> response = userController.getUserById(user.getUuid());

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(user);
    }

    // Shhould return 404 Not Found
    @Test
    public void testGetUserNotFound() {
        // Arrange
        when(userService.findById(user.getUuid())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<User> response = userController.getUserById(user.getUuid());

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    // Test for the createUser method
    // Should return 201 Created and a user
    @Test
    public void testCreateUser() {
        // Arrange
        when(userService.insert(user)).thenReturn(user);
        when(userService.findById(user.getUuid())).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<User> response = userController.createUser(user);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(user);
    }

    //should return INTERNAL_SERVER_ERROR
    @Test
    public void testCreateUserInternalServerError() {
        // Arrange
        when(userService.insert(user)).thenReturn(null);
        when(userService.findById(user.getUuid())).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<User> response = userController.createUser(user);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // should return INTERNAL_SERVER_ERROR
    @Test
    public void testCreateUserInternalServerError2() {
        // Arrange
        when(userService.insert(user)).thenReturn(user);
        when(userService.findById(user.getUuid())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<User> response = userController.createUser(user);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Test for the updateUser method
    // Should return 200 OK and a user
    @Test
    public void testUpdateUser() {
        // Arrange
        when(userService.save(user)).thenReturn(user);
        when(userService.findById(user.getUuid())).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<User> response = userController.updateUser(user.getUuid(), user);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(user);
    }

    // should return 404 Not Found
    @Test
    public void testUpdateUserNotFound() {
        // Arrange
        when(userService.save(user)).thenReturn(null);

        // Act
        ResponseEntity<User> response = userController.updateUser(user.getUuid(), user);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    // Test for the deleteUser method
    // Should return 200 OK and a user
    @Test
    public void testDeleteUser() {
        // Arrange
        when(userService.deleteByUuid(user.getUuid())).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<User> response = userController.deleteUser(user.getUuid());

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(user);
    }

    // should return 404 Not Found
    @Test
    public void testDeleteUserNotFound() {
        // Arrange
        when(userService.deleteByUuid(user.getUuid())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<User> response = userController.deleteUser(user.getUuid());

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}
