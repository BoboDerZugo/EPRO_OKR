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
@RestController
@RequestMapping("/keyresult")
public class KeyResultController {

    @Autowired
    private KeyResultService keyResultService;
    @Autowired
    private KeyResultHistoryService keyResultHistoryService;

    @GetMapping
    public ResponseEntity<List<KeyResult>> getAllKeyResults() {
        List<KeyResult> keyResults = keyResultService.findAll();
        return ResponseEntity.ok(keyResults);
    }

    @GetMapping("/{id}")
    public ResponseEntity<KeyResult> getKeyResultById(@PathVariable("id") UUID id) {
        Optional<KeyResult> keyResult = keyResultService.findById(id);
        if (keyResult.isPresent()) {
            return ResponseEntity.ok(keyResult.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //??
    @GetMapping("/{id}/fullfillment")
    // Returns the fullfillment of a key result JSON
    public ResponseEntity<String> getKeyResultFullfillment(@PathVariable("id") UUID id) {
        Optional<KeyResult> keyResult = keyResultService.findById(id);
        if (keyResult.isPresent()) {
            KeyResult keyRes = keyResult.get();
            // JSON String
            /*
             * {
             * "description": "description",
             * "fullfillment": "fullfillment",
             * "goal": "goal",
             * "current": "current",
             * "contributingUnit" : "contributingUnit"
             * 
             * }
             */
            String fullfillment =  "{\"description\": \"" + keyRes.getDescription() + "\", \"fullfillment\": \"" + 
                    keyRes.getFulfilled() + "\", \"goal\": \"" + keyRes.getGoal() + "\", \"current\": \"" + keyRes.getCurrent() + "\"}";
            return ResponseEntity.ok(fullfillment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<KeyResult> createKeyResult(@RequestBody @NonNull KeyResult keyResult) {
        KeyResult createdKeyResult = keyResultService.insert(keyResult);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdKeyResult);
    }

    @PutMapping("/{id}")
    public ResponseEntity<KeyResult> updateKeyResult(@PathVariable("id") UUID id, @RequestBody KeyResult keyResult) {
        // insert key result history
        keyResultHistoryService.insert(new KeyResultHistory(keyResult));
        // update key result
        Optional<KeyResult> keyResultToUpdate = keyResultService.updateOne(id, keyResult);
        if (keyResultToUpdate.isPresent()) {
            return ResponseEntity.ok(keyResultToUpdate.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<KeyResult> deleteKeyResult(@PathVariable("id") UUID id) {
        Optional<KeyResult> keyResultToDelete = keyResultService.delete(id);
        if (keyResultToDelete.isPresent()) {
            return ResponseEntity.ok(keyResultToDelete.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
