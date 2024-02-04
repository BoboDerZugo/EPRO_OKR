
package com.example.controller;

import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/user")
public class UserController {

        @Autowired
        private UserService userService;

        /**
         * This method returns all users.
         *
         * @return ResponseEntity<List<User>>
         */
        @GetMapping
        public ResponseEntity<List<User>> getAllUsers() {
                List<User> users = userService.findAll();
                return ResponseEntity.ok(users);
        }

        /**
         * This method returns a user by id.
         *
         * @param id
         * @return ResponseEntity<User>
         */
        @GetMapping("/{id}")
        public ResponseEntity<User> getUserById(@PathVariable("id") @NonNull UUID id) {
                Optional<User> user = userService.findById(id);
                if (user.isPresent()) {
                        return ResponseEntity.ok(user.get());
                } else {
                        return ResponseEntity.notFound().build();
                }
        }

        /**
         * This method creates a user.
         *
         * @param user
         * @return ResponseEntity<User>
         */
        @PostMapping
        public ResponseEntity<User> createUser(@RequestBody @NonNull User user) {
                User createdUser = userService.insert(user);
                Optional<User> userOptional = userService.findById(createdUser.getUuid());
                if (userOptional.isPresent()) {
                        return ResponseEntity.status(HttpStatus.CREATED).body(userOptional.get());
                }
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        /**
         * This method updates a user.
         *
         * @param id
         * @param user
         * @return ResponseEntity<User>
         */
        @PutMapping("/{id}")
        public ResponseEntity<User> updateUser(@PathVariable("id") @NonNull UUID id, @RequestBody @NonNull User user) {
                user.setUuid(UUID.fromString(id.toString()));
                User updatedUser = userService.save(user);
                if (updatedUser != null) {
                        return ResponseEntity.ok(updatedUser);
                }
                return ResponseEntity.notFound().build();
        }

        /**
         * This method deletes a user.
         *
         * @param id
         * @return ResponseEntity<User>
         */
        @DeleteMapping("/{id}")
        public ResponseEntity<User> deleteUser(@PathVariable("id") @NonNull UUID id) {
                Optional<User> userToDelete = userService.deleteByUuid(id);
                if (userToDelete.isPresent()) {
                        return ResponseEntity.ok(userToDelete.get());
                } else {
                        return ResponseEntity.notFound().build();
                }
        }

}