package com.example.controller;

import com.example.service.KeyResultService;
import com.example.service.KeyResultHistoryService;

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
    public ResponseEntity<List<KeyResult>> getAllKeyResults(){
        List<KeyResult> keyResults = keyResultService.findAll();
        return new ResponseEntity<>(keyResults, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<KeyResult> getKeyResultById(@PathVariable("id") UUID id){
        Optional<KeyResult> keyResult = keyResultService.findById(id);
        if (keyResult.isPresent()) {
            return new ResponseEntity<>(keyResult.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<KeyResult> createKeyResult(@RequestBody  @NonNull KeyResult keyResult){
        KeyResult createdKeyResult = keyResultService.insert(keyResult);
        return new ResponseEntity<>(createdKeyResult, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<KeyResult> updateKeyResult(@PathVariable("id") UUID id, @RequestBody KeyResult keyResult){
        //insert key result history
        keyResultHistoryService.insert(new KeyResultHistory(keyResult));
        //update key result
        Optional<KeyResult> keyResultToUpdate = keyResultService.updateOne(id, keyResult);
        if (keyResultToUpdate.isPresent()) {
            return new ResponseEntity<>(keyResultToUpdate.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<KeyResult> deleteKeyResult(@PathVariable("id") UUID id){
        Optional<KeyResult> keyResultToDelete = keyResultService.delete(id);
        if (keyResultToDelete.isPresent()) {
            return new ResponseEntity<>(keyResultToDelete.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

