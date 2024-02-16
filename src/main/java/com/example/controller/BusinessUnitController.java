//Busines Unit Controller
package com.example.controller;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import com.example.model.BusinessUnit;
import com.example.model.Company;
import com.example.service.BusinessUnitService;
import com.example.service.CompanyService;
import com.example.model.Unit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

/**
 * This class represents the controller for managing business units.
 */
@RestController
@RequestMapping(value = { "/businessunit", "/company/{companyId}/businessunit" })
public class BusinessUnitController {

    @Autowired
    private BusinessUnitService businessUnitService;
    @Autowired
    private CompanyService companyService;

    /**
     * Retrieves all business units for a given company.
     *
     * @param companyId The ID of the company.
     * @return The response entity containing a set of business units.
     */
    @GetMapping
    public ResponseEntity<Set<BusinessUnit>> getAllBusinessUnits(@PathVariable("companyId") Optional<UUID> companyId) {
        if (companyId.isPresent()) {
            Company company = companyService.findById(companyId.get()).get();
            Set<BusinessUnit> businessUnits = company.getBusinessUnits();
            return ResponseEntity.ok(businessUnits);
        }
        return ResponseEntity.notFound().build();
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
     * Creates a new business unit for a given company.
     *
     * @param businessUnit The business unit to be created.
     * @param companyId    The ID of the company.
     * @return The response entity containing the created business unit.
     */
    @PostMapping
    public ResponseEntity<BusinessUnit> createBusinessUnit(@RequestBody @NonNull BusinessUnit businessUnit,
            @PathVariable("companyId") Optional<UUID> companyId) {
        if (companyId.isPresent()) {
            Company company = companyService.findById(companyId.get()).get();
            businessUnitService.insert(businessUnit);
            company.addBusinessUnit(businessUnit);
            companyService.save(company);
            return ResponseEntity.status(HttpStatus.CREATED).body(businessUnit);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Updates an existing business unit.
     *
     * @param id            The ID of the business unit to be updated.
     * @param businessUnit  The updated business unit.
     * @param companyId     The ID of the company.
     * @return The response entity containing the updated business unit.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BusinessUnit> updateBusinessUnit(@PathVariable("id") @NonNull UUID id,
            @RequestBody BusinessUnit businessUnit, @PathVariable("companyId") Optional<UUID> companyId) {
        if (companyId.isPresent()) {
            Company company = companyService.findById(companyId.get()).get();
            if (company.getBusinessUnits().contains(businessUnit)) {
                businessUnit.setUuid(id);
                businessUnitService.save(businessUnit);
                return ResponseEntity.ok(businessUnit);
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Deletes a business unit.
     *
     * @param id            The ID of the business unit to be deleted.
     * @param companyId     The ID of the company.
     * @return The response entity containing the deleted business unit.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<BusinessUnit> deleteBusinessUnit(@PathVariable("id") UUID id,
            @PathVariable("companyId") Optional<UUID> companyId) {
        if (companyId.isPresent()) {
            Company company = companyService.findById(companyId.get()).get();
            Optional<BusinessUnit> businessUnit = businessUnitService.findById(id);
            if (businessUnit.isPresent()) {
                if (company.getBusinessUnits().contains(businessUnit.get())) {
                    company.getBusinessUnits().remove(businessUnit.get());
                    companyService.save(company);
                    businessUnitService.delete(businessUnit.get());
                    return ResponseEntity.ok(businessUnit.get());
                }
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            return ResponseEntity.notFound().build();

        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
