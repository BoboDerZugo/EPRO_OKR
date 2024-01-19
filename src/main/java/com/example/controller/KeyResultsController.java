package com.example.controller;

import com.example.service.KeyResultService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.model.*;
import java.util.List;


//CRUD operations for KeyResults
@RestController
@RequestMapping("/keyresult")
public class KeyResultController {

    private final KeyResultService keyResultService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<KeyResult> getAllKeyResults(){
        return keyResultService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public KeyResult getKeyResultById(@PathVariable("id") Long id){
        return keyResultService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KeyResult createKeyResult(@RequestBody KeyResult keyResult){
        return keyResultService.insertOne(keyResult);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public KeyResult updateKeyResult(@PathVariable("id") Long id, @RequestBody KeyResult keyResult){
        return keyResultService.updateOne(id, keyResult);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteKeyResult(@PathVariable("id") Long id){
        keyResultService.deleteOne(id);
    }
}

