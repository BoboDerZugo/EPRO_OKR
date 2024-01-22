package com.example.controller;

import com.example.service.UnitService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import com.example.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/unit")
public class UnitController {
    
    @Autowired
    private UnitService unitService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Unit> getAllUnits(){
        return unitService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Unit getUnitById(@PathVariable("id") UUID id){
        Optional<Unit> unit = unitService.findById(id);
        return unit.get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Unit createUnit(@RequestBody @NonNull Unit unit){
        return unitService.save(unit);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Unit updateUnit(@PathVariable("id") @NonNull Long id, @RequestBody @NonNull Unit unit){
        Optional<Unit> unitToUpdate = unitService.updateOne(id, unit);
        return unitToUpdate.get();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Unit deleteUnit(@PathVariable("id") @NonNull Long id){
        Optional<Unit> unitToDelete = unitService.delete(id);
        return unitToDelete.get();
    }
}