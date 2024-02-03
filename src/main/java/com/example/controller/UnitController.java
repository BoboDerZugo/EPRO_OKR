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
        return ResponseEntity.ok(units);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Unit> getUnitById(@PathVariable("id") @NonNull UUID id) {
        Optional<Unit> unit = unitService.findById(id);
        if (unit.isPresent()) {
            return ResponseEntity.ok(unit.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Unit> createUnit(@RequestBody @NonNull Unit unit) {
        Unit createdUnit = unitService.insert(unit);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUnit);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Unit> updateUnit(@PathVariable("id") @NonNull UUID id, @RequestBody Unit unit) {
        if (unit != null) {
            Unit updatedUnit = unitService.save(unit);
            if (updatedUnit != null) {
                return ResponseEntity.ok(updatedUnit);
            }
        }
        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Unit> deleteUnit(@PathVariable("id") @NonNull UUID id) {
        Optional<Unit> unitToDelete = unitService.deleteByUuid(id);
        if (unitToDelete.isPresent()) {
            return ResponseEntity.ok(unitToDelete.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}