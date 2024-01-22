package com.example.controller;

import com.example.service.UnitService;
import com.example.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import com.example.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * This class represents a controller for managing units.
 */
@RestController
@RequestMapping("/unit")
public class UserController{
        
        @Autowired
        private UserService userService;

        /**
         * This method returns all users.
         * @return List<User>
         */
        @GetMapping
        @ResponseStatus(HttpStatus.OK)
        public List<User> getAllUsers(){
                return userService.findAll();
        }

        /**
         * This method returns a user by id.
         * @param id
         * @return User
         */
        @GetMapping("/{id}")
        @ResponseStatus(HttpStatus.OK)
        public User getUserById(@PathVariable("id") @NonNull UUID id){
                Optional<User> user = userService.findById(id);
                return user.get();
        }

        /**
         * This method creates a user.
         * @param user
         * @return User
         */
        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        public User createUser(@RequestBody @NonNull User user){
                return userService.save(user);
        }

        /**
         * This method updates a user.
         * @param id
         * @param user
         * @return User
         */
        @PutMapping("/{id}")
        @ResponseStatus(HttpStatus.OK)
        public User updateUser(@PathVariable("id") UUID id, @RequestBody User user){
                Optional<User> userToUpdate = userService.updateOne(id, user);
                return userToUpdate.get();
        }

        /**
         * This method deletes a user.
         * @param id
         */
        @DeleteMapping("/{id}")
        @ResponseStatus(HttpStatus.OK)
        public User deleteUser(@PathVariable("id") Long id){
                Optional<User> userToDelete = userService.delete(id);
                return userToDelete.get();
        }

        
    }