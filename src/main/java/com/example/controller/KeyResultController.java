package com.example.controller;

import com.example.service.KeyResultService;
import com.example.service.KeyResultHistoryService;
import com.example.model.KeyResult;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.model.*;
import java.util.List;
import java.util.UUID;

import org.springframework.lang.NonNull;

//CRUD operations for KeyResults
/**
 * Controller class for managing Key Results.
 */
@RestController
@RequestMapping("/keyresult")
public class KeyResultController {

    @Autowired
    private KeyResultService keyResultService;
    @Autowired
    private KeyResultHistoryService keyResultHistoryService;

    /**
     * Get all Key Results.
     *
     * @return The list of Key Results.
     */
    @GetMapping
    public ResponseEntity<List<KeyResult>> getAllKeyResults() {
        List<KeyResult> keyResults = keyResultService.findAll();
        return ResponseEntity.ok(keyResults);
    }

    /**
     * Get a Key Result by ID.
     *
     * @param id The ID of the Key Result.
     * @return The Key Result if found, otherwise returns a not found response.
     */
    @GetMapping("/{id}")
    public ResponseEntity<KeyResult> getKeyResultById(@PathVariable("id") @NonNull UUID id) {
        Optional<KeyResult> keyResult = keyResultService.findById(id);
        if (keyResult.isPresent()) {
            return ResponseEntity.ok(keyResult.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Create a new Key Result.
     *
     * @param keyResult The Key Result to create.
     * @return The created Key Result if successful, otherwise returns an internal server error response.
     */
    @PostMapping
    public ResponseEntity<KeyResult> createKeyResult(@RequestBody @NonNull KeyResult keyResult) {
        KeyResult createdKeyResult = keyResultService.insert(keyResult);
        keyResultHistoryService.insert(new KeyResultHistory(createdKeyResult));
        UUID keyResultUuid = createdKeyResult.getUuid();
        if(keyResultUuid != null) {
            Optional<KeyResult> keyResultOptional = keyResultService.findById(keyResultUuid);
            if (keyResultOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(keyResultOptional.get());
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * Update a Key Result.
     *
     * @param id The ID of the Key Result to update.
     * @param keyResult The updated Key Result.
     * @return The updated Key Result if successful, otherwise returns a not found response.
     */
    @PutMapping("/{id}")
    public ResponseEntity<KeyResult> updateKeyResult(@PathVariable("id") UUID id,
            @RequestBody KeyResult keyResult) {
        // insert key result history;
        keyResultHistoryService.insert(new KeyResultHistory(keyResult));
        keyResult.setUuid(UUID.fromString(id.toString()));
        KeyResult updatedKeyResult = keyResultService.save(keyResult);
        updatedKeyResult = keyResultService.findById(id).get();
        // if (updatedKeyResult != null) {
        return ResponseEntity.ok(updatedKeyResult);
        // }
        // return ResponseEntity.notFound().build();
    }

    /**
     * Delete a Key Result.
     *
     * @param id The ID of the Key Result to delete.
     * @return The deleted Key Result if found, otherwise returns a not found response.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<KeyResult> deleteKeyResult(@PathVariable("id") UUID id) {
        Optional<KeyResult> keyResultToDelete = keyResultService.deleteByUuid(id);
        if (keyResultToDelete.isPresent()) {
            return ResponseEntity.ok(keyResultToDelete.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
