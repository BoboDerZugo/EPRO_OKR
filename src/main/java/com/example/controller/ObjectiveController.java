package com.example.controller;

import com.example.service.ObjectiveService;

import java.util.List;
import java.util.UUID;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.OK)
    public List<Objective> getAllObjectives(){
        return objectiveService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Objective getObjectiveById(@PathVariable("id") @NonNull UUID id){
        Optional<Objective> objective = objectiveService.findById(id);
        return objective.get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Objective createObjective(@RequestBody @NonNull Objective objective){
        return objectiveService.insert(objective);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Objective updateObjective(@PathVariable("id") UUID id, @RequestBody Objective objective){
        //update objective
        Optional<Objective> objectiveToUpdate = objectiveService.updateOne(id, objective);
        return objectiveToUpdate.get();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Objective deleteObjective(@PathVariable("id") @NonNull UUID id){
        Optional<Objective> objectiveToDelete = objectiveService.findByIdAndDelete(id);
        return objectiveToDelete.get();
    }
}
