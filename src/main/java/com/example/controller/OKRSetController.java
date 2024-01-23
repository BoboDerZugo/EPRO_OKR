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

@RestController
@RequestMapping("/okrset")
public class OKRSetController {
    
    //CRUD operations for OKRSet
    @Autowired
    private OKRSetService okrSetService;

    @GetMapping
    public ResponseEntity<List<OKRSet>> getAllOKRSets(){
        List<OKRSet> okrSets = okrSetService.findAll();
        return ResponseEntity.ok(okrSets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OKRSet> getOKRSetById(@PathVariable("id") @NonNull UUID id){
        Optional<OKRSet> okrSet = okrSetService.findById(id);
        if (okrSet.isPresent()) {
            return ResponseEntity.ok(okrSet.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<OKRSet> createOKRSet(@RequestBody @NonNull OKRSet okrSet){
        OKRSet createdOKRSet = okrSetService.insert(okrSet);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOKRSet);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OKRSet> updateOKRSet(@PathVariable("id") @NonNull UUID id, @RequestBody OKRSet okrSet){
        Optional<OKRSet> okrSetToUpdate = okrSetService.updateOne(id, okrSet);
        if (okrSetToUpdate.isPresent()) {
            return ResponseEntity.ok(okrSetToUpdate.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OKRSet> deleteOKRSet(@PathVariable("id") @NonNull UUID id){
        Optional<OKRSet> okrSetToDelete = okrSetService.delete(id);
        if (okrSetToDelete.isPresent()) {
            return ResponseEntity.ok(okrSetToDelete.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
