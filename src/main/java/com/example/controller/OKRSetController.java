/**
 * This package contain the controller classes for managing OKR sets.
 */
package com.example.controller;

import com.example.service.AuthorizationService;
import com.example.service.BusinessUnitService;
import com.example.service.CompanyService;
import com.example.service.OKRSetService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;
import com.example.model.*;

/**
 * This class represents the controller for managing OKRSet entities.
 */
/**
 * Controller class for managing OKRSet entities.
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

    @GetMapping
    public ResponseEntity<Set<OKRSet>> getAllOKRSets(@PathVariable("companyId") Optional<UUID> companyId,
            @PathVariable("buId") Optional<UUID> buId) {
        if (companyId.isPresent()) {
            if (buId.isPresent()) {
                BusinessUnit businessUnit = businessUnitService.findById(buId.get()).get();
                Set<OKRSet> okrSets = businessUnit.getOkrSets();
                return ResponseEntity.ok(okrSets);
            } else {
                Company company = companyService.findById(companyId.get()).get();
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

    @PostMapping
    public ResponseEntity<OKRSet> createOKRSet(@RequestBody @NonNull OKRSet okrSet,
            @PathVariable("companyId") Optional<UUID> companyId,
            @PathVariable("buId") Optional<UUID> buId) {
        if (companyId.isPresent()) {
            Company company = companyService.findById(companyId.get()).get();
            if (buId.isPresent()) {
                BusinessUnit businessUnit = businessUnitService.findById(buId.get()).get();
                if (AuthorizationService.isAuthorized(company, businessUnit)) {
                    okrSetService.insert(okrSet);
                    businessUnit.addOkrSet(okrSet);
                    businessUnitService.save(businessUnit);
                }
            } else {
                if (AuthorizationService.isAuthorized(company, null)) {
                    okrSetService.insert(okrSet);
                    company.addOkrSet(okrSet);
                    companyService.save(company);
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<OKRSet> updateOKRSet(@PathVariable("id") @NonNull UUID id,
            @RequestBody @NonNull OKRSet okrSet, @PathVariable("companyId") Optional<UUID> companyId,
            @PathVariable("buId") Optional<UUID> buId) {
        if (companyId.isPresent()) {
            Company company = companyService.findById(companyId.get()).get();
            if (buId.isPresent()) {
                BusinessUnit businessUnit = businessUnitService.findById(buId.get()).get();
                if (AuthorizationService.isAuthorized(company, businessUnit)) {
                    if (businessUnit.getOkrSets().removeIf(okrSet1 -> okrSet1.getUuid().equals(id))) {
                        okrSet.setUuid(id);
                        okrSetService.save(okrSet);
                        businessUnit.addOkrSet(okrSet);
                        businessUnitService.save(businessUnit);
                    }
                }
            } else {
                if (AuthorizationService.isAuthorized(company, null)) {
                    if (company.getOkrSets().removeIf(okrSet1 -> okrSet1.getUuid().equals(id))) {
                        okrSet.setUuid(id);
                        okrSetService.save(okrSet);
                        company.addOkrSet(okrSet);
                        companyService.save(company);
                    }
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OKRSet> deleteOKRSet(@PathVariable("id") @NonNull UUID id,
            @PathVariable("companyId") Optional<UUID> companyId,
            @PathVariable("buId") Optional<UUID> buId) {
        if (companyId.isPresent()) {
            Company company = companyService.findById(companyId.get()).get();
            if (buId.isPresent()) {
                BusinessUnit businessUnit = businessUnitService.findById(buId.get()).get();
                if (AuthorizationService.isAuthorized(company, businessUnit)) {
                    if (businessUnit.getOkrSets().removeIf(okrSet -> okrSet.getUuid().equals(id))) {
                        okrSetService.deleteByUuid(id);
                        businessUnitService.save(businessUnit);
                        return ResponseEntity.ok().build();
                    }
                }
            } else {
                if (AuthorizationService.isAuthorized(company, null)) {
                    if (company.getOkrSets().removeIf(okrSet -> okrSet.getUuid().equals(id))) {
                        okrSetService.deleteByUuid(id);
                        companyService.save(company);
                        return ResponseEntity.ok().build();
                    }
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
