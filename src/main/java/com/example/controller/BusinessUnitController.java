//Busines Unit Controller

package com.example.controller;

import java.util.List;

import com.example.model.BusinessUnit;
import com.example.model.BusinessUnitModel;
import com.example.model.OKRSet;
import com.example.model.User;
import com.example.service.BusinessUnitService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/businessunit")
public class BusinessUnitController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BusinessUnit> getAllBusinessUnits(){
        return businessUnitService.getAllBusinessUnits();
    }

    //get units by business unit id
    @GetMapping("/units/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<BusinessUnit> getUnitsByBusinessUnitId(@PathVariable("id") Long id){
        return businessUnitService.getUnitsByBusinessUnitId(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BusinessUnit getBusinessUnitById(@PathVariable("id") Long id){
        return businessUnitService.getBusinessUnitById(id);
    }

    @GetMapping("/{id}/okr")
    @ResponseStatus(HttpStatus.OK)
    public List<OKRSet> getOKRsByBusinessUnitId(@PathVariable("id") Long id){
        return businessUnitService.getOKRsByBusinessUnitId(id);
    }

    @GetMapping("/{id}/employees")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getEmployeesByBusinessUnitId(@PathVariable("id") Long id){
        return businessUnitService.getEmployeesByBusinessUnitId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BusinessUnit createBusinessUnit(@RequestBody BusinessUnit businessUnit){
        return businessUnitService.createBusinessUnit(businessUnit);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BusinessUnit updateBusinessUnit(@PathVariable("id") Long id, @RequestBody BusinessUnit businessUnit){
        return businessUnitService.updateBusinessUnit(id, businessUnit);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBusinessUnit(@PathVariable("id") Long id){
        businessUnitService.deleteBusinessUnit(id);
    }
}
