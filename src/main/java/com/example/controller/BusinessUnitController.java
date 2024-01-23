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
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/businessunit")
public class BusinessUnitController {

    @Autowired
    private BusinessUnitService businessUnitService;

    @GetMapping
    public ResponseEntity<List<BusinessUnit>> getAllBusinessUnits() {
        List<BusinessUnit> businessUnits = businessUnitService.findAll();
        return ResponseEntity.ok(businessUnits);
    }

    // get units by business unit id
    @GetMapping("/units/{id}")
    public ResponseEntity<Set<Unit>> getUnitsByBusinessUnitId(@PathVariable("id") @NonNull UUID id) {
        Optional<BusinessUnit> businessUnit = businessUnitService.findById(id);
        if (businessUnit.isPresent()) {
            Set<Unit> units = businessUnit.get().getUnits();
            return ResponseEntity.ok(units);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusinessUnit> getBusinessUnitById(@PathVariable("id") @NonNull UUID id) {
        Optional<BusinessUnit> businessUnit = businessUnitService.findById(id);
        if (businessUnit.isPresent()) {
            BusinessUnit unit = businessUnit.get();
            return ResponseEntity.ok(unit);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/okr")
    public ResponseEntity<Set<OKRSet>> getOKRsByBusinessUnitId(@PathVariable("id") @NonNull UUID id) {
        Optional<BusinessUnit> businessUnit = businessUnitService.findById(id);
        if (businessUnit.isPresent()) {
            Set<OKRSet> okrSets = businessUnit.get().getOkrSets();
            return ResponseEntity.ok(okrSets);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/employees")
    public ResponseEntity<Set<User>> getEmployeesByBusinessUnitId(@PathVariable("id") @NonNull UUID id) {
        Optional<BusinessUnit> businessUnit = businessUnitService.findById(id);
        if (businessUnit.isPresent()) {
            Set<User> employees = businessUnit.get().getEmployeeSet();
            return ResponseEntity.ok(employees);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<BusinessUnit> createBusinessUnit(@RequestBody @NonNull BusinessUnit businessUnit) {
        BusinessUnit createdBusinessUnit = businessUnitService.insert(businessUnit);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBusinessUnit);
    }

    // Add OKRSet to BusinessUnit
    @PostMapping("/{id}/okrset")
    public ResponseEntity<BusinessUnit> addOKRSetToBusinessUnit(@PathVariable("id") @NonNull UUID id, @RequestBody @NonNull OKRSet okrSet) {
        Optional<BusinessUnit> businessUnit = businessUnitService.findById(id);
        if (businessUnit.isPresent()) {
            BusinessUnit bu = businessUnit.get();
            if (bu.getOkrSets().size() > 4) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            bu.addOKRSet(okrSet);
            BusinessUnit updatedBusinessUnit = businessUnitService.updateOne(id, bu).get();
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedBusinessUnit);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BusinessUnit> updateBusinessUnit(@PathVariable("id") @NonNull UUID id, @RequestBody BusinessUnit businessUnit) {
        Optional<BusinessUnit> businessUnitToUpdate = businessUnitService.updateOne(id, businessUnit);
        if (businessUnitToUpdate.isPresent()) {
            BusinessUnit updatedBusinessUnit = businessUnitToUpdate.get();
            return ResponseEntity.ok(updatedBusinessUnit);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BusinessUnit> deleteBusinessUnit(@PathVariable("id") UUID id) {
        Optional<BusinessUnit> businessUnitToDelete = businessUnitService.delete(id);
        if (businessUnitToDelete.isPresent()) {
            BusinessUnit deletedBusinessUnit = businessUnitToDelete.get();
            return ResponseEntity.ok(deletedBusinessUnit);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
