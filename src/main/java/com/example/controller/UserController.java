package com.example.controller;

import com.example.service.UnitService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.model.*;
import java.util.List;

@RestController
@RequestMapping("/unit")
public class UnitController {
    
    private final UnitService unitService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Unit> getAllUnits(){
        return unitService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Unit getUnitById(@PathVariable("id") Long id){
        return unitService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Unit createUnit(@RequestBody Unit unit){
        return unitService.insertOne(unit);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Unit updateUnit(@PathVariable("id") Long id, @RequestBody Unit unit){
        return unitService.updateOne(id, unit);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUnit(@PathVariable("id") Long id){
        unitService.deleteOne(id);
    }
}