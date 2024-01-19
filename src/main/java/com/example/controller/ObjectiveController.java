package com.example.controller;

import com.example.service.ObjectiveService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.model.*;

@RestController
@RequestMapping("/objective")
public class ObjectiveController {
    
    //CRUD operations for Objectives
    private final ObjectiveService objectiveService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Objective> getAllObjectives(){
        return objectiveService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Objective getObjectiveById(@PathVariable("id") Long id){
        return objectiveService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Objective createObjective(@RequestBody Objective objective){
        return objectiveService.insertOne(objective);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Objective updateObjective(@PathVariable("id") Long id, @RequestBody Objective objective){
        return objectiveService.updateOne(id, objective);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteObjective(@PathVariable("id") Long id){
        objectiveService.deleteOne(id);
    }
}
