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

    @GetMapping
    public ResponseEntity<Objective> getObjective(@PathVariable("companyId") Optional<UUID> companyId,
            @PathVariable("buId") Optional<UUID> buId, @PathVariable("okrId") Optional<UUID> okrId) {

        if (companyId.isPresent()) {
            //Company company = companyService.findById(companyId.get()).get();
            if (buId.isPresent()) {
                //BusinessUnit businessUnit = businessUnitService.findById(buId.get()).get();
                if (okrId.isPresent()) {
                    OKRSet okrSet = okrSetService.findById(okrId.get()).get();
                    Objective objectives = okrSet.getObjective();

                    return ResponseEntity.ok(objectives);
                }
            } else if (okrId.isPresent()) {
                OKRSet okrSet = okrSetService.findById(okrId.get()).get();
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

    @PostMapping
    public ResponseEntity<Objective> createObjective(@RequestBody @NonNull Objective objective,
            @PathVariable("companyId") Optional<UUID> companyId,
            @PathVariable("buId") Optional<UUID> buId, @PathVariable("okrId") Optional<UUID> okrId) {
        if (companyId.isPresent()) {
            Company company = companyService.findById(companyId.get()).get();
            if (buId.isPresent()) {
                BusinessUnit businessUnit = businessUnitService.findById(buId.get()).get();
                if (okrId.isPresent()) {
                    OKRSet okrSet = okrSetService.findById(okrId.get()).get();
                    if (okrSet.getObjective() != null) {
                        return ResponseEntity.status(HttpStatus.CONFLICT).build();
                    }
                    if (AuthorizationService.isAuthorized(company, businessUnit)) {
                        objectiveService.insert(objective);
                        okrSet.setObjective(objective);
                        okrSetService.save(okrSet);
                        return ResponseEntity.status(HttpStatus.CREATED).body(okrSet.getObjective());
                    }
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            } else if (okrId.isPresent()) {
                OKRSet okrSet = okrSetService.findById(okrId.get()).get();
                if (okrSet.getObjective() != null) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).build();
                }
                if (AuthorizationService.isAuthorized(company, null)) {
                    objectiveService.insert(objective);
                    okrSet.setObjective(objective);
                    okrSetService.save(okrSet);
                    return ResponseEntity.status(HttpStatus.CREATED).body(okrSet.getObjective());
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping
    public ResponseEntity<Objective> updateObjective(
            @RequestBody @NonNull Objective objective, @PathVariable("companyId") Optional<UUID> companyId,
            @PathVariable("buId") Optional<UUID> buId, @PathVariable("okrId") Optional<UUID> okrId) {
        if (companyId.isPresent()) {
            Company company = companyService.findById(companyId.get()).get();
            if (buId.isPresent()) {
                BusinessUnit businessUnit = businessUnitService.findById(buId.get()).get();
                if (okrId.isPresent()) {
                    OKRSet okrSet = okrSetService.findById(okrId.get()).get();
                    objective.setUuid(okrSet.getObjective().getUuid());
                    if (AuthorizationService.isAuthorized(company, businessUnit)) {
                        objectiveService.save(objective);
                        okrSet.setObjective(objective);
                        okrSetService.save(okrSet);
                        return ResponseEntity.ok(okrSet.getObjective());
                    }
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            } else if (okrId.isPresent()) {
                OKRSet okrSet = okrSetService.findById(okrId.get()).get();
                objective.setUuid(okrSet.getObjective().getUuid());
                if (AuthorizationService.isAuthorized(company, null)) {
                    objectiveService.save(objective);
                    okrSet.setObjective(objective);
                    okrSetService.save(okrSet);
                    return ResponseEntity.ok(okrSet.getObjective());
                }
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }

    @DeleteMapping
    public ResponseEntity<Objective> deleteObjective(
            @PathVariable("companyId") Optional<UUID> companyId,
            @PathVariable("buId") Optional<UUID> buId, @PathVariable("okrId") Optional<UUID> okrId) {
        if (companyId.isPresent()) {
            Company company = companyService.findById(companyId.get()).get();
            if (buId.isPresent()) {
                BusinessUnit businessUnit = businessUnitService.findById(buId.get()).get();
                if (okrId.isPresent()) {
                    OKRSet okrSet = okrSetService.findById(okrId.get()).get();
                    if (AuthorizationService.isAuthorized(company, businessUnit)) {
                        objectiveService.deleteById(okrSet.getObjective().getUuid());
                        okrSet.setObjective(null);
                        okrSetService.save(okrSet);
                        return ResponseEntity.ok(okrSet.getObjective());
                    }
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            } else if (okrId.isPresent()) {
                OKRSet okrSet = okrSetService.findById(okrId.get()).get();
                if (AuthorizationService.isAuthorized(company, null)) {
                    objectiveService.deleteById(okrSet.getObjective().getUuid());
                    okrSet.setObjective(null);
                    okrSetService.save(okrSet);
                    return ResponseEntity.ok(okrSet.getObjective());
                }
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }
}
