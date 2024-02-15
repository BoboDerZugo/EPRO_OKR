//Busines Unit Controller

package com.example.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.example.model.BusinessUnit;
//import com.example.model.User;
import com.example.service.BusinessUnitService;
import com.example.model.Unit;
import com.example.model.OKRSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

/**
 * This class represents the controller for managing business units.
 */
@RestController
@RequestMapping("/businessunit")
public class BusinessUnitController {

    @Autowired
    private BusinessUnitService businessUnitService;

    /**
     * Retrieves all business units.
     *
     * @return The response entity containing a list of business units.
     */
    @GetMapping
    public ResponseEntity<List<BusinessUnit>> getAllBusinessUnits() {
        List<BusinessUnit> businessUnits = businessUnitService.findAll();
        return ResponseEntity.ok(businessUnits);
    }

    /**
     * Retrieves units by business unit id.
     *
     * @param id The business unit id.
     * @return The response entity containing a set of units.
     */
    @GetMapping("/{id}/units")
    public ResponseEntity<Set<Unit>> getUnitsByBusinessUnitId(@PathVariable("id") @NonNull UUID id) {
        Optional<BusinessUnit> businessUnit = businessUnitService.findById(id);
        if (businessUnit.isPresent()) {
            Set<Unit> units = businessUnit.get().getUnits();
            return ResponseEntity.ok(units);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves a business unit by id.
     *
     * @param id The business unit id.
     * @return The response entity containing a business unit.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BusinessUnit> getBusinessUnitById(@PathVariable("id") @NonNull UUID id) {
        Optional<BusinessUnit> businessUnit = businessUnitService.findById(id);
        if (businessUnit.isPresent()) {
            BusinessUnit unit = businessUnit.get();
            if (unit != null) {
                return ResponseEntity.ok(unit);
            }
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Retrieves OKR sets by business unit id.
     *
     * @param id The business unit id.
     * @return The response entity containing a set of OKR sets.
     */
    @GetMapping("/{id}/okrset")
    public ResponseEntity<Set<OKRSet>> getOKRsByBusinessUnitId(@PathVariable("id") @NonNull UUID id) {
        Optional<BusinessUnit> businessUnit = businessUnitService.findById(id);
        if (businessUnit.isPresent()) {
            Set<OKRSet> okrSets = businessUnit.get().getOkrSets();
            if (okrSets != null) {
                return ResponseEntity.ok(okrSets);
            }
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Retrieves an OKR set by business unit id and OKR set id.
     *
     * @param id       The business unit id.
     * @param okrSetId The OKR set id.
     * @return The response entity containing an OKR set.
     */
    @GetMapping("/{id}/okrset/{okrSetId}")
    public ResponseEntity<OKRSet> getOKRByBusinessUnitId(@PathVariable("id") @NonNull UUID id,
            @PathVariable("okrSetId") @NonNull UUID okrSetId) {
        Optional<BusinessUnit> businessUnit = businessUnitService.findById(id);
        if (businessUnit.isPresent()) {
            BusinessUnit bu = businessUnit.get();
            for (OKRSet okrSet : bu.getOkrSets()) {
                if (okrSet.getUuid().equals(okrSetId)) {
                    return ResponseEntity.ok(okrSet);
                }
            }
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Creates a new business unit.
     *
     * @param businessUnit The business unit to be created.
     * @return The response entity containing the created business unit.
     */
    @PostMapping
    public ResponseEntity<BusinessUnit> createBusinessUnit(@RequestBody @NonNull BusinessUnit businessUnit) {
        BusinessUnit createdBusinessUnit = businessUnitService.insert(businessUnit);
        UUID businessUnitUuid = createdBusinessUnit.getUuid();
        if (businessUnitUuid != null) {
            Optional<BusinessUnit> businessUnitOptional = businessUnitService.findById(businessUnitUuid);
            if (businessUnitOptional.isPresent()) {
                BusinessUnit bu = businessUnitOptional.get();
                if (bu != null)
                    return ResponseEntity.status(HttpStatus.CREATED).body(bu);
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * Adds an OKR set to a business unit.
     *
     * @param id     The business unit id.
     * @param okrSet The OKR set to be added.
     * @return The response entity containing the updated business unit.
     */
    @PostMapping("/okrset/{id}")
    public ResponseEntity<BusinessUnit> addOKRSetToBusinessUnit(@PathVariable("id") @NonNull UUID id,
            @RequestBody @NonNull OKRSet okrSet) {
        Optional<BusinessUnit> businessUnit = businessUnitService.findById(id);
        if (businessUnit.isPresent()) {
            BusinessUnit bu = businessUnit.get();
            if (bu.getOkrSets().size() > 4) {
                return ResponseEntity.badRequest().build();
            }
            bu.addOKRSet(okrSet);
            BusinessUnit updatedBusinessUnit = businessUnitService.save(bu);
            if (updatedBusinessUnit != null)
                return ResponseEntity.ok(updatedBusinessUnit);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Updates a business unit.
     *
     * @param id           The business unit id.
     * @param businessUnit The updated business unit.
     * @return The response entity containing the updated business unit.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BusinessUnit> updateBusinessUnit(@PathVariable("id") @NonNull UUID id,
            @RequestBody BusinessUnit businessUnit) {
        businessUnit.setUuid(UUID.fromString(id.toString()));
        BusinessUnit updatedBusinessUnit = businessUnitService.save(businessUnit);
        updatedBusinessUnit = businessUnitService.findById(id).get();
        // if (updatedBusinessUnit != null)
        return ResponseEntity.ok(updatedBusinessUnit);

        // return ResponseEntity.notFound().build();

    }

    /**
     * Updates a Business Unit OKRSet.
     * @param id The business unit id.
     * @param okrSetId The OKRSet id.
     * @param okrSet The updated OKRSet.
     * @return The response entity containing the updated business unit.
     */
    @PutMapping("/{id}/okrset/{okrSetId}")
    public ResponseEntity<BusinessUnit> updateBusinessUnitOKRSet(@PathVariable("id") @NonNull UUID id,
            @PathVariable("okrSetId") @NonNull UUID okrSetId, @RequestBody OKRSet okrSet) {
        Optional<BusinessUnit> businessUnit = businessUnitService.findById(id);
        if (businessUnit.isPresent()) {
            BusinessUnit bu = businessUnit.get();
            bu.getOkrSets().removeIf(okr -> okr.getUuid().equals(okrSetId));
            okrSet.setUuid(okrSetId);
            bu.addOKRSet(okrSet);
            BusinessUnit updatedBusinessUnit = businessUnitService.save(bu);
            if (updatedBusinessUnit != null)
                return ResponseEntity.ok(updatedBusinessUnit);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Deletes a business unit.
     *
     * @param id The business unit id.
     * @return The response entity containing the deleted business unit.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<BusinessUnit> deleteBusinessUnit(@PathVariable("id") UUID id) {
        Optional<BusinessUnit> businessUnitToDelete = businessUnitService.deleteByUuid(id);
        if (businessUnitToDelete.isPresent()) {
            BusinessUnit deletedBusinessUnit = businessUnitToDelete.get();
            if (deletedBusinessUnit != null)
                return ResponseEntity.ok(deletedBusinessUnit);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Deletes an OKR set from a business unit.
     *
     * @param id       The business unit id.
     * @param okrSetId The OKR set id.
     * @return The response entity containing the updated business unit.
     */
    @DeleteMapping("/{id}/okrset/{okrSetId}")
    public ResponseEntity<BusinessUnit> deleteOKRSetFromBusinessUnit(@PathVariable("id") @NonNull UUID id,
            @PathVariable("okrSetId") @NonNull UUID okrSetId) {
        Optional<BusinessUnit> businessUnit = businessUnitService.findById(id);
        if (businessUnit.isPresent()) {
            BusinessUnit bu = businessUnit.get();
            bu.getOkrSets().removeIf(okrSet -> okrSet.getUuid().equals(okrSetId));
            BusinessUnit updatedBusinessUnit = businessUnitService.save(bu);
            if (updatedBusinessUnit != null)
                return ResponseEntity.ok(updatedBusinessUnit);
        }
        return ResponseEntity.notFound().build();
    }
}
