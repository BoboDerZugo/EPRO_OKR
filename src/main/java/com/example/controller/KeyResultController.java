package com.example.controller;

import com.example.service.KeyResultService;
import com.example.service.KeyResultHistoryService;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.OK)
    public List<KeyResult> getAllKeyResults(){
        return keyResultService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public KeyResult getKeyResultById(@PathVariable("id") UUID id){
        Optional<KeyResult> keyResult = keyResultService.findById(id);
        return keyResult.get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KeyResult createKeyResult(@RequestBody  @NonNull KeyResult keyResult){
        return keyResultService.insert(keyResult);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public KeyResult updateKeyResult(@PathVariable("id") UUID id, @RequestBody KeyResult keyResult){
        //insert key result history
        keyResultHistoryService.insert(new KeyResultHistory(keyResult));
        //update key result
        Optional<KeyResult> keyResultToUpdate = keyResultService.updateOne(id, keyResult);
        return keyResultToUpdate.get();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public KeyResult deleteKeyResult(@PathVariable("id") UUID id){
        Optional<KeyResult> keyResultToDelete = keyResultService.delete(id);
        return keyResultToDelete.get();
    }
}

