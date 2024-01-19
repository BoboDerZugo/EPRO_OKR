package com.example.controller;

import com.example.service.UnitService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.model.*;
import java.util.List;

/**
 * This class represents a controller for managing units.
 */
@RestController
@RequestMapping("/unit")
public class UserController{
        
        private final UserService userService;

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
        public User getUserById(@PathVariable("id") Long id){
                return userService.findById(id);
        }

        /**
         * This method creates a user.
         * @param user
         * @return User
         */
        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        public User createUser(@RequestBody User user){
                return userService.insertOne(user);
        }

        /**
         * This method updates a user.
         * @param id
         * @param user
         * @return User
         */
        @PutMapping("/{id}")
        @ResponseStatus(HttpStatus.OK)
        public User updateUser(@PathVariable("id") Long id, @RequestBody User user){
                return userService.updateOne(id, user);
        }

        /**
         * This method deletes a user.
         * @param id
         */
        @DeleteMapping("/{id}")
        @ResponseStatus(HttpStatus.OK)
        public void deleteUser(@PathVariable("id") Long id){
                userService.deleteOne(id);
        }

        
    }