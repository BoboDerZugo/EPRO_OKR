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
    
    //CRUD operations for Objectives
    @Autowired
    private ObjectiveService objectiveService;

    @GetMapping
    public ResponseEntity<List<Objective>> getAllObjectives(){
        List<Objective> objectives = objectiveService.findAll();
        return new ResponseEntity<>(objectives, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Objective> getObjectiveById(@PathVariable("id") @NonNull UUID id){
        Optional<Objective> objective = objectiveService.findById(id);
        if (objective.isPresent()) {
            return new ResponseEntity<>(objective.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Objective> createObjective(@RequestBody @NonNull Objective objective){
        Objective createdObjective = objectiveService.insert(objective);
        return new ResponseEntity<>(createdObjective, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Objective> updateObjective(@PathVariable("id") UUID id, @RequestBody Objective objective){
        Optional<Objective> objectiveToUpdate = objectiveService.updateOne(id, objective);
        if (objectiveToUpdate.isPresent()) {
            return new ResponseEntity<>(objectiveToUpdate.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Objective> deleteObjective(@PathVariable("id") @NonNull UUID id){
        Optional<Objective> objectiveToDelete = objectiveService.findByIdAndDelete(id);
        if (objectiveToDelete.isPresent()) {
            return new ResponseEntity<>(objectiveToDelete.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
