/**
 * This package contain the controller classes for managing OKR sets.
 */
package com.example.controller;

import com.example.service.AuthorizationService;
import com.example.service.BusinessUnitService;
import com.example.service.CompanyService;
import com.example.service.OKRSetService;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import com.example.model.*;

/**
 * This class represents the controller for managing OKRSet entities.
 */
@RestController
@RequestMapping(value = { "/okrset", "/company/{companyId}/okrset", "/company/{companyId}/businessunit/{buId}/okrset" })
public class OKRSetController {

    // CRUD operations for OKRSet
    @Autowired
    private OKRSetService okrSetService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private BusinessUnitService businessUnitService;

    /**
     * Retrieves all OKR sets for a given company (and business unit).
     *
     * @param companyId the ID of the company
     * @param buId      the ID of the business unit
     * @throws IllegalArgumentException if the company or business unit is not found
     * @return ResponseEntity containing the set of OKR sets if found, or a not
     *         found response if not
     */
    @GetMapping
    public ResponseEntity<Set<OKRSet>> getAllOKRSets(@PathVariable("companyId") @NonNull Optional<UUID> companyId,
            @PathVariable("buId") Optional<UUID> buId) {
        if (companyId.isPresent()) {
            if (buId.isPresent()) {
                BusinessUnit businessUnit = businessUnitService.findById(buId.get())
                        .orElse(null);
                if (businessUnit == null) {
                    return ResponseEntity.notFound().build();
                }
                Set<OKRSet> okrSets = businessUnit.getOkrSets();
                return ResponseEntity.ok(okrSets);
            } else {
                Company company = companyService.findById(companyId.get())
                        .orElse(null);
                if (company == null) {
                    return ResponseEntity.notFound().build();
                }
                Set<OKRSet> okrSets = company.getOkrSets();
                return ResponseEntity.ok(okrSets);
            }
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get an OKRSet by ID.
     *
     * @param id the ID of the OKRSet
     * @return ResponseEntity containing the OKRSet object if found, or a not found
     *         response
     */
    @GetMapping("/{id}")
    public ResponseEntity<OKRSet> getOKRSetById(@PathVariable("id") @NonNull UUID id) {
        Optional<OKRSet> okrSet = okrSetService.findById(id);
        if (okrSet.isPresent()) {
            return ResponseEntity.ok(okrSet.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Create a new OKRSet.
     *
     * @param okrSet    the OKRSet to create
     * @param companyId the ID of the company
     * @param buId      the ID of the business unit
     * @throws IllegalArgumentException if the company or business unit is not found
     * @return ResponseEntity containing the OKRSet if created, or a not authorized
     *         response
     */
    @PostMapping
    public ResponseEntity<OKRSet> createOKRSet(@RequestBody @NonNull OKRSet okrSet,
            @PathVariable("companyId") @NonNull Optional<UUID> companyId,
            @PathVariable("buId") Optional<UUID> buId) {
        if (companyId.isPresent()) {
            Company company = companyService.findById(companyId.get())
                    .orElse(null);
            if (company == null) {
                return ResponseEntity.notFound().build();
            }
            if (buId.isPresent()) {
                BusinessUnit businessUnit = businessUnitService.findById(buId.get())
                        .orElse(null);
                if (businessUnit == null) {
                    return ResponseEntity.notFound().build();
                }
                if (AuthorizationService.isAuthorized(company, businessUnit, null)) {
                    okrSetService.insert(okrSet);
                    businessUnit.addOkrSet(okrSet);
                    businessUnitService.save(businessUnit);
                    return ResponseEntity.ok(okrSet);
                }
            } else {
                System.out.println(AuthorizationService.isAuthorized(company, null, null));
                if (AuthorizationService.isAuthorized(company, null, null)) {
                    okrSetService.insert(okrSet);
                    company.addOkrSet(okrSet);
                    companyService.save(company);
                    return ResponseEntity.ok(okrSet);
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Update an OKRSet.
     *
     * @param id        the ID of the OKRSet
     * @param okrSet    the OKRSet to update
     * @param companyId the ID of the company
     * @param buId      the ID of the business Unit
     * @throws IllegalArgumentException if the company or business unit is not found
     * @return ResponseEntity containing the updated OKRSet if successful, or a not
     *         authorized response
     */
    @PutMapping("/{id}")
    public ResponseEntity<OKRSet> updateOKRSet(@PathVariable("id") @NonNull UUID id,
            @RequestBody @NonNull OKRSet okrSet, @PathVariable("companyId") @NonNull Optional<UUID> companyId,
            @PathVariable("buId") Optional<UUID> buId) {
        if (companyId.isPresent()) {
            Company company = companyService.findById(companyId.get())
                    .orElse(null);
                    if (company == null) {
                        return ResponseEntity.notFound().build();
                    }
            if (buId.isPresent()) {
                BusinessUnit businessUnit = businessUnitService.findById(buId.get())
                        .orElse(null);
                if (businessUnit == null) {
                    return ResponseEntity.notFound().build();
                }
                OKRSet okrSetToUpdate = okrSetService.findById(id).get();
                if (AuthorizationService.isAuthorized(company, businessUnit, okrSetToUpdate)) {
                    okrSet.setUuid(id);
                    okrSetService.save(okrSet);
                    return ResponseEntity.ok(okrSet);
                }
            } else {
                if (AuthorizationService.isAuthorized(company, null, null)) {
                    okrSet.setUuid(id);
                    okrSetService.save(okrSet);
                    return ResponseEntity.ok(okrSet);
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Delete an OKRSet.
     *
     * @param id        the ID of the OKRSet
     * @param companyId the ID of the company
     * @param buId      the ID of the business unit
     * @throws IllegalArgumentException if the company or business unit is not found
     * @return ResponseEntity containing the OKRSet if deleted, or a not authorized
     *         response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<OKRSet> deleteOKRSet(@PathVariable("id") @NonNull UUID id,
            @PathVariable("companyId") @NonNull Optional<UUID> companyId,
            @PathVariable("buId") Optional<UUID> buId) {
        if (companyId.isPresent()) {
            Company company = companyService.findById(companyId.get())
                    .orElse(null);
            if (company == null) {
                return ResponseEntity.notFound().build();
            }
            if (buId.isPresent()) {
                BusinessUnit businessUnit = businessUnitService.findById(buId.get())
                        .orElse(null);
                OKRSet okrSet = okrSetService.findById(id)
                        .orElse(null);
                if (businessUnit == null || okrSet == null) {
                    return ResponseEntity.notFound().build();
                }
                if (AuthorizationService.isAuthorized(company, businessUnit, okrSet)) {
                    if (businessUnit.getOkrSets().removeIf(okr -> okr.getUuid().equals(id))) {
                        okrSetService.deleteByUuid(id);
                        businessUnitService.save(businessUnit);
                        return ResponseEntity.ok(okrSet);
                    }
                }
            } else {
                if (AuthorizationService.isAuthorized(company, null, null)) {
                    company.getOkrSets().forEach(okr -> System.out.println(okr.getUuid()));
                    OKRSet okrSet = okrSetService.findById(id)
                            .orElse(null);
                    if (okrSet == null) {
                        return ResponseEntity.notFound().build();
                    }
                    if (company.getOkrSets().removeIf(okr -> okr.getUuid().equals(id))) {
                        System.err.println("OKRSet removed from company");
                        okrSetService.deleteByUuid(id);
                        companyService.save(company);
                        return ResponseEntity.ok(okrSet);
                    }
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
