//Busines Unit Controller

package com.example.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.example.model.BusinessUnit;
import com.example.model.User;
import com.example.service.BusinessUnitService;
import com.example.model.Unit;
import com.example.model.OKRSet;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/businessunit")
public class BusinessUnitController {

    @Autowired
    private BusinessUnitService businessUnitService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BusinessUnit> getAllBusinessUnits(){
        return businessUnitService.findAll();
    }

    //get units by business unit id
    @GetMapping("/units/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Set<Unit> getUnitsByBusinessUnitId(@PathVariable("id") @NonNull UUID id){
        Optional<BusinessUnit> businessUnit = businessUnitService.findById(id);
        return businessUnit.get().getUnits();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BusinessUnit getBusinessUnitById(@PathVariable("id") @NonNull UUID id){
        Optional<BusinessUnit> businessUnit = businessUnitService.findById(id);
        return businessUnit.get();
    }

    @GetMapping("/{id}/okr")
    @ResponseStatus(HttpStatus.OK)
    public Set<OKRSet> getOKRsByBusinessUnitId(@PathVariable("id") @NonNull UUID id){
        Optional<BusinessUnit> businessUnit = businessUnitService.findById(id);
        return businessUnit.get().getOkrSets();
    }

    @GetMapping("/{id}/employees")
    @ResponseStatus(HttpStatus.OK)
    public Set<User> getEmployeesByBusinessUnitId(@PathVariable("id") @NonNull UUID id){
        Optional<BusinessUnit> businessUnit = businessUnitService.findById(id);
        return businessUnit.get().getEmployeeSet();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BusinessUnit createBusinessUnit(@RequestBody @NonNull BusinessUnit businessUnit){
        return businessUnitService.insert(businessUnit);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BusinessUnit updateBusinessUnit(@PathVariable("id") @NonNull UUID id, @RequestBody BusinessUnit businessUnit){
        Optional<BusinessUnit> businessUnitToUpdate = businessUnitService.updateOne(id, businessUnit);
        return businessUnitToUpdate.get();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BusinessUnit deleteBusinessUnit(@PathVariable("id") UUID id){
        Optional<BusinessUnit> businessUnitToDelete = businessUnitService.delete(id);
        return businessUnitToDelete.get();
    }
}
