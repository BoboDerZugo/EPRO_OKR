package com.example.controller;

import com.example.service.ObjectiveService;

import java.util.List;
import java.util.UUID;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import com.example.model.*;

@RestController
@RequestMapping("/objective")
public class ObjectiveController {

    // CRUD operations for Objectives
    @Autowired
    private ObjectiveService objectiveService;

    @GetMapping
    public ResponseEntity<List<Objective>> getAllObjectives() {
        List<Objective> objectives = objectiveService.findAll();
        return ResponseEntity.ok(objectives);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Objective> getObjectiveById(@PathVariable("id") @NonNull UUID id) {
        Optional<Objective> objective = objectiveService.findById(id);
        if (objective.isPresent()) {
            return ResponseEntity.ok(objective.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Objective> createObjective(@RequestBody @NonNull Objective objective) {
        Objective createdObjective = objectiveService.insert(objective);
        UUID uuid = createdObjective.getUuid();
        if (uuid != null) {
            Optional<Objective> newObjective = objectiveService.findById(uuid);
            if (newObjective.isPresent()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(newObjective.get());
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Objective> updateObjective(@PathVariable("id") UUID id,
            @RequestBody @NonNull Objective objective) {
        objective.setUuid(UUID.fromString(id.toString()));
        Objective updatedObjective = objectiveService.save(objective);
        updatedObjective = objectiveService.findById(id).get();
        // if (updatedObjective != null) {
        return ResponseEntity.ok(updatedObjective);
        // }
        // return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Objective> deleteObjective(@PathVariable("id") @NonNull UUID id) {
        Optional<Objective> objectiveToDelete = objectiveService.deleteByUuid(id);
        if (objectiveToDelete.isPresent()) {
            return ResponseEntity.ok(objectiveToDelete.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
