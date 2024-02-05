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

/**
 * The UnitController class handles HTTP requests related to units.
 */
@RestController
@RequestMapping("/unit")
public class UnitController {

    @Autowired
    private UnitService unitService;

    /**
     * Retrieves all units.
     *
     * @return ResponseEntity containing a list of units
     */
    @GetMapping
    public ResponseEntity<List<Unit>> getAllUnits() {
        List<Unit> units = unitService.findAll();
        return ResponseEntity.ok(units);
    }

    /**
     * Retrieves a unit by its ID.
     *
     * @param id the ID of the unit to retrieve
     * @return ResponseEntity containing the unit if found, or a not found response if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Unit> getUnitById(@PathVariable("id") @NonNull UUID id) {
        Optional<Unit> unit = unitService.findById(id);
        if (unit.isPresent()) {
            return ResponseEntity.ok(unit.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Creates a new unit.
     *
     * @param unit the unit to create
     * @return ResponseEntity containing the created unit if successful, or an internal server error response if not successful
     */
    @PostMapping
    public ResponseEntity<Unit> createUnit(@RequestBody @NonNull Unit unit) {
        Unit createdUnit = unitService.insert(unit);
        UUID unitUuid = createdUnit.getUuid();
        if (unitUuid != null) {
            Optional<Unit> unitOptional = unitService.findById(unitUuid);
            if (unitOptional.isPresent()) {
                System.out.println("Unit created" + unitOptional.get());
                return ResponseEntity.status(HttpStatus.CREATED).body(unitOptional.get());
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * Updates a unit.
     *
     * @param id   the ID of the unit to update
     * @param unit the updated unit
     * @return ResponseEntity containing the updated unit if successful, or a not found response if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Unit> updateUnit(@PathVariable("id") @NonNull UUID id, @RequestBody @NonNull Unit unit) {
        unit.setUuid(UUID.fromString(id.toString()));
        Unit updatedUnit = unitService.save(unit);
        updatedUnit = unitService.findById(id).get();
        // if (updatedUnit != null) {
        return ResponseEntity.ok(updatedUnit);
        // }
        // return ResponseEntity.notFound().build();

    }

    /**
     * Deletes a unit.
     *
     * @param id the ID of the unit to delete
     * @return ResponseEntity containing the deleted unit if successful, or a not found response if not found
     */
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