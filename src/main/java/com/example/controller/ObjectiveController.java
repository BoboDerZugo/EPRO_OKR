package com.example.controller;

import com.example.service.AuthorizationService;
import com.example.service.BusinessUnitService;
import com.example.service.CompanyService;
import com.example.service.OKRSetService;
import com.example.service.ObjectiveService;
import java.util.UUID;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import com.example.model.*;

/**
 * This class represents the controller for managing Objective entities.
 */
@RestController
@RequestMapping(value = { "/objective", "/company/{companyId}/okrset/{okrId}/objective",
        "/company/{companyId}/businessunit/{buId}/okrset/{okrId}/objective" })
public class ObjectiveController {

    // CRUD operations for Objectives
    @Autowired
    private ObjectiveService objectiveService;
    @Autowired
    private BusinessUnitService businessUnitService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private OKRSetService okrSetService;

    /**
     * Retrieves all objectives for a given company, (business unit), and OKRSet.
     *
     * @param companyId the ID of the company
     * @param buId      the ID of the business unit
     * @param okrId     the ID of the OKRSet
     * @return ResponseEntity containing the set of objectives if found, or a not
     *         found response if not
     */
    @GetMapping
    public ResponseEntity<Objective> getObjective(@PathVariable("companyId") @NonNull Optional<UUID> companyId,
            @PathVariable("buId") Optional<UUID> buId, @PathVariable("okrId") @NonNull Optional<UUID> okrId) {

        if (companyId.isPresent()) {
            // Company company = companyService.findById(companyId.get()).get();
            if (buId.isPresent()) {
                // BusinessUnit businessUnit = businessUnitService.findById(buId.get()).get();
                if (okrId.isPresent()) {
                    OKRSet okrSet = okrSetService.findById(okrId.get()).orElse(null);
                    if (okrSet == null) {
                        return ResponseEntity.notFound().build();
                    }
                    Objective objectives = okrSet.getObjective();
                    return ResponseEntity.ok(objectives);
                }
            } else if (okrId.isPresent()) {
                OKRSet okrSet = okrSetService.findById(okrId.get()).orElse(null);
                if (okrSet == null) {
                    return ResponseEntity.notFound().build();
                }
                Objective objectives = okrSet.getObjective();
                return ResponseEntity.ok(objectives);
            }
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Retrieves an objective by its ID.
     *
     * @param id the ID of the objective to retrieve
     * @return ResponseEntity containing the objective if found, or a not found
     *         response if not
     */
    @GetMapping("/{id}")
    public ResponseEntity<Objective> getObjectiveById(@PathVariable("id") @NonNull UUID id) {
        Optional<Objective> objective = objectiveService.findById(id);
        if (objective.isPresent()) {
            return ResponseEntity.ok(objective.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Creates a new objective for a given company, (business unit), and OKRSet.
     *
     * @param objective the objective to create
     * @param companyId the ID of the company
     * @param buId      the ID of the business unit
     * @param okrId     the ID of the OKRSet
     * @return ResponseEntity containing the created objective if successful, or a
     *         conflict response if the objective already exists
     */
    @PostMapping
    public ResponseEntity<Objective> createObjective(@RequestBody @NonNull Objective objective,
            @PathVariable("companyId") @NonNull Optional<UUID> companyId,
            @PathVariable("buId") Optional<UUID> buId, @PathVariable("okrId") @NonNull Optional<UUID> okrId) {
        boolean isAuthorized = false;
        if (companyId.isPresent()) {
            Company company = companyService.findById(companyId.get())
                    .orElse(null);
            if (company == null) {
                return ResponseEntity.notFound().build();
            }
            OKRSet okrSet = okrId.isPresent() ? okrSetService.findById(okrId.get()).orElse(null) : null;
            if (okrSet == null) {
                return ResponseEntity.notFound().build();
            }
            // Check if the user is authorized to create a key result for the given company,
            // business unit, and OKRSet
            if (buId.isPresent()) {
                BusinessUnit businessUnit = businessUnitService.findById(buId.get())
                        .orElse(null);
                if (businessUnit == null) {
                    return ResponseEntity.notFound().build();
                }
                if (AuthorizationService.isAuthorized(company, businessUnit, okrSet)) {
                    isAuthorized = true;
                }
            }
            // CO Admins can change any OKRSet
            else if (AuthorizationService.isAuthorized(company, null, null)) {
                isAuthorized = true;
            }
            if (isAuthorized) {
                if (okrSet.getObjective() != null) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).build();
                }
                objectiveService.insert(objective);
                okrSet.setObjective(objective);
                okrSetService.save(okrSet);
                return ResponseEntity.status(HttpStatus.CREATED).body(okrSet.getObjective());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }

    /**
     * Updates an existing objective for a given company, (business unit),and
     * OKRSet.
     *
     * @param objective the updated objective
     * @param companyId the ID of the company
     * @param buId      the ID of the business unit
     * @param okrId     the ID of the OKRSet
     * @return ResponseEntity containing the updated objective if successful, or a
     *         not found response if the objective does not exist
     */
    @PutMapping
    public ResponseEntity<Objective> updateObjective(
            @RequestBody @NonNull Objective objective, @PathVariable("companyId") @NonNull Optional<UUID> companyId,
            @PathVariable("buId") Optional<UUID> buId, @PathVariable("okrId") @NonNull Optional<UUID> okrId) {
        boolean isAuthorized = false;
        if (companyId.isPresent()) {
            Company company = companyService.findById(companyId.get())
                    .orElse(null);
            if (company == null) {
                return ResponseEntity.notFound().build();
            }
            OKRSet okrSet = okrId.isPresent() ? okrSetService.findById(okrId.get()).orElse(null) : null;
            if (okrSet == null) {
                return ResponseEntity.notFound().build();
            }
            // Check if the user is authorized to create a key result for the given company,
            // business unit, and OKRSet
            if (buId.isPresent()) {
                BusinessUnit businessUnit = businessUnitService.findById(buId.get())
                        .orElse(null);
                if (businessUnit == null) {
                    return ResponseEntity.notFound().build();
                }
                if (AuthorizationService.isAuthorized(company, businessUnit, okrSet)) {
                    isAuthorized = true;
                }
            }
            // CO Admins can change any OKRSet
            else if (AuthorizationService.isAuthorized(company, null, null)) {
                isAuthorized = true;
            }
            if (isAuthorized) {
                objective.setUuid(okrSet.getObjective().getUuid());
                objectiveService.save(objective);
                return ResponseEntity.ok(objective);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }

    /**
     * Deletes an existing objective for a given company, (business unit), and
     * OKRSet.
     *
     * @param companyId the ID of the company
     * @param buId      the ID of the business unit
     * @param okrId     the ID of the OKRSet
     * @return ResponseEntity containing the deleted objective if successful, or a
     *         not found response if the objective does not exist
     */
    @DeleteMapping
    public ResponseEntity<Objective> deleteObjective(
            @PathVariable("companyId") @NonNull Optional<UUID> companyId,
            @PathVariable("buId") Optional<UUID> buId, @PathVariable("okrId") @NonNull Optional<UUID> okrId) {
        boolean isAuthorized = false;
        if (companyId.isPresent()) {
            Company company = companyService.findById(companyId.get())
                    .orElse(null);
            if (company == null) {
                return ResponseEntity.notFound().build();
            }
            OKRSet okrSet = okrId.isPresent() ? okrSetService.findById(okrId.get()).orElse(null) : null;
            if (okrSet == null) {
                return ResponseEntity.notFound().build();
            }
            // Check if the user is authorized to create a key result for the given company,
            // business unit, and OKRSet
            if (buId.isPresent()) {
                BusinessUnit businessUnit = businessUnitService.findById(buId.get())
                        .orElse(null);
                if (businessUnit == null) {
                    return ResponseEntity.notFound().build();
                }
                if (AuthorizationService.isAuthorized(company, businessUnit, okrSet)) {
                    isAuthorized = true;
                }
            }
            // CO Admins can change any OKRSet
            else if (AuthorizationService.isAuthorized(company, null, null)) {
                isAuthorized = true;
            }
            if (isAuthorized) {
                Objective objective = okrSet.getObjective();
                objectiveService.deleteByUuid(objective.getUuid());
                okrSet.setObjective(null);
                okrSetService.save(okrSet);
                return ResponseEntity.ok(objective);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }
}
