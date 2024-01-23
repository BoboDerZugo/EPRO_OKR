package com.example.controller;

import com.example.service.UnitService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Unit>> getAllUnits() {
        List<Unit> units = unitService.findAll();
        return new ResponseEntity<>(units, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Unit> getUnitById(@PathVariable("id") @NonNull UUID id) {
        Optional<Unit> unit = unitService.findById(id);
        if (unit.isPresent()) {
            return new ResponseEntity<>(unit.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Unit> createUnit(@RequestBody @NonNull Unit unit) {
        Unit createdUnit = unitService.insert(unit);
        return new ResponseEntity<>(createdUnit, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Unit> updateUnit(@PathVariable("id") @NonNull UUID id, @RequestBody @NonNull Unit unit) {
        Optional<Unit> unitToUpdate = unitService.updateOne(id, unit);
        if (unitToUpdate.isPresent()) {
            return new ResponseEntity<>(unitToUpdate.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Unit> deleteUnit(@PathVariable("id") @NonNull UUID id) {
        Optional<Unit> unitToDelete = unitService.delete(id);
        if (unitToDelete.isPresent()) {
            return new ResponseEntity<>(unitToDelete.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}