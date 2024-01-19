package com.example.controller;

import com.example.service.OKRSetService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.model.*;

@RestController
@RequestMapping("/okrset")
public class OKRSetController {
    
    //CRUD operations for OKRSet
    private final OKRSetService okrSetService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OKRSet> getAllOKRSets(){
        return okrSetService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OKRSet getOKRSetById(@PathVariable("id") Long id){
        return okrSetService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OKRSet createOKRSet(@RequestBody OKRSet okrSet){
        return okrSetService.insertOne(okrSet);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OKRSet updateOKRSet(@PathVariable("id") Long id, @RequestBody OKRSet okrSet){
        return okrSetService.updateOne(id, okrSet);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteOKRSet(@PathVariable("id") Long id){
        okrSetService.deleteOne(id);
    }
}
