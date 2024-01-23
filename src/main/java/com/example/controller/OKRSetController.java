package com.example.controller;

import com.example.service.OKRSetService;

import java.lang.annotation.Native;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.OK)
    public List<OKRSet> getAllOKRSets(){
        return okrSetService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OKRSet getOKRSetById(@PathVariable("id") @NonNull UUID id){
        Optional<OKRSet> okrSet = okrSetService.findById(id);
        return okrSet.get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OKRSet createOKRSet(@RequestBody @NonNull OKRSet okrSet){
        return okrSetService.insert(okrSet);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OKRSet updateOKRSet(@PathVariable("id") @NonNull UUID id, @RequestBody OKRSet okrSet){
        Optional<OKRSet> okrSetToUpdate = okrSetService.updateOne(id, okrSet);
        return okrSetToUpdate.get();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OKRSet deleteOKRSet(@PathVariable("id") @NonNull UUID id){
        Optional<OKRSet> okrSetToDelete = okrSetService.delete(id);
        return okrSetToDelete.get();
    }
}
