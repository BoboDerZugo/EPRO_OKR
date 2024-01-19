//Busines Unit Controller

package com.example.controller;

import java.util.List;
import java.util.Set;

import com.example.model.BusinessUnit;
import com.example.model.User;
import com.example.service.BusinessUnitService;
import com.example.model.Unit;
import com.example.model.OKRSet;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/businessunit")
public class BusinessUnitController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<BusinessUnit> getAllBusinessUnits(){
        return businessUnitService.findAll();
    }

    //get units by business unit id
    @GetMapping("/units/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Set<Unit> getUnitsByBusinessUnitId(@PathVariable("id") Long id){
        BusinessUnit businessUnit = businessUnitService.findById(id);
        return businessUnit.getUnits();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BusinessUnit getBusinessUnitById(@PathVariable("id") Long id){
        return businessUnitService.findById(id);
    }

    @GetMapping("/{id}/okr")
    @ResponseStatus(HttpStatus.OK)
    public Set<OKRSet> getOKRsByBusinessUnitId(@PathVariable("id") Long id){
        BusinessUnit businessUnit = businessUnitService.findById(id);
        return businessUnit.getOKRSets();
    }

    @GetMapping("/{id}/employees")
    @ResponseStatus(HttpStatus.OK)
    public Set<User> getEmployeesByBusinessUnitId(@PathVariable("id") Long id){
        BusinessUnit businessUnit = businessUnitService.findById(id);
        return businessUnit.getEmployees();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BusinessUnit createBusinessUnit(@RequestBody BusinessUnit businessUnit){
        //create business unit
        return businessUnitService.insertBusinessUnit(businessUnit);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BusinessUnit updateBusinessUnit(@PathVariable("id") Long id, @RequestBody BusinessUnit businessUnit){
        businessUnitService.updateOne(id, businessUnit);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBusinessUnit(@PathVariable("id") Long id){
        businessUnitService.deleteOne(id);
    }
}
