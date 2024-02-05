/**
 * This package contain the controller classes for managing OKR sets.
 */
package com.example.controller;

import com.example.service.OKRSetService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import com.example.model.*;

/**
 * This class represents the controller for managing OKRSet entities.
 */
/**
 * Controller class for managing OKRSet entities.
 */
@RestController
@RequestMapping("/okrset")
public class OKRSetController {

    // CRUD operations for OKRSet
    @Autowired
    private OKRSetService okrSetService;

    /**
     * Get all OKRSets.
     *
     * @return ResponseEntity containing a list of OKRSet objects
     */
    @GetMapping
    public ResponseEntity<List<OKRSet>> getAllOKRSets() {
        List<OKRSet> okrSets = okrSetService.findAll();
        return ResponseEntity.ok(okrSets);
    }

    /**
     * Get an OKRSet by ID.
     *
     * @param id the ID of the OKRSet
     * @return ResponseEntity containing the OKRSet object if found, or a not found response
     */
    @GetMapping("/{id}")
    public ResponseEntity<OKRSet> getOKRSetById(@PathVariable("id") @NonNull UUID id) {
        Optional<OKRSet> okrSet = okrSetService.findById(id);
        if (okrSet.isPresent()) {
            return ResponseEntity.ok(okrSet.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Create a new OKRSet.
     *
     * @param okrSet the OKRSet object to create
     * @return ResponseEntity containing the created OKRSet object if successful, or an error response
     */
    @PostMapping
    public ResponseEntity<OKRSet> createOKRSet(@RequestBody @NonNull OKRSet okrSet) {
        OKRSet createdOKRSet = okrSetService.insert(okrSet);
        UUID okrSetUuid = createdOKRSet.getUuid();
        if (okrSetUuid != null) {
            Optional<OKRSet> okrSetOptional = okrSetService.findById(okrSetUuid);
            if (okrSetOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(okrSetOptional.get());
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * Update an existing OKRSet.
     *
     * @param id      the ID of the OKRSet to update
     * @param okrSet  the updated OKRSet object
     * @return ResponseEntity containing the updated OKRSet object if successful, or an error response
     */
    @PutMapping("/{id}")
    public ResponseEntity<OKRSet> updateOKRSet(@PathVariable("id") @NonNull UUID id, @RequestBody @NonNull OKRSet okrSet) {
        okrSet.setUuid(UUID.fromString(id.toString()));
        OKRSet updatedOKRSet = okrSetService.save(okrSet);
        updatedOKRSet = okrSetService.findById(id).get();
        return ResponseEntity.ok(updatedOKRSet);
    }

    /**
     * Delete an OKRSet by ID.
     *
     * @param id the ID of the OKRSet to delete
     * @return ResponseEntity containing the deleted OKRSet object if found, or a not found response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<OKRSet> deleteOKRSet(@PathVariable("id") @NonNull UUID id) {
        Optional<OKRSet> okrSetToDelete = okrSetService.deleteByUuid(id);
        if (okrSetToDelete.isPresent()) {
            return ResponseEntity.ok(okrSetToDelete.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
