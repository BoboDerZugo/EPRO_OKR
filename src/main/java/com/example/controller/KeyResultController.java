package com.example.controller;

import com.example.service.KeyResultService;
import com.example.service.OKRSetService;
import com.example.service.AuthorizationService;
import com.example.service.BusinessUnitService;
import com.example.service.CompanyService;
import com.example.service.KeyResultHistoryService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.model.*;
import java.util.List;
import java.util.UUID;
import org.springframework.lang.NonNull;


//CRUD operations for KeyResults
/**
 * Controller class for managing Key Results.
 */
@RestController
@RequestMapping(value = { "/keyresult", "/company/{companyId}/okrset/{okrId}/keyresult",
        "/company/{companyId}/businessunit/{buId}/okrset/{okrId}/keyresult" })
public class KeyResultController {

    @Autowired
    private KeyResultService keyResultService;
    @Autowired
    private KeyResultHistoryService keyResultHistoryService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private OKRSetService okrSetService;
    @Autowired
    private BusinessUnitService businessUnitService;

    /**
     * Retrieves all Key Results for a given company, (business unit), and OKRSet.
     * @param companyId
     * @param buId
     * @param okrId
     * @return
     */
    @GetMapping
    public ResponseEntity<List<KeyResult>> getAllKeyResults(@PathVariable("companyId") Optional<UUID> companyId,
            @PathVariable("buId") Optional<UUID> buId, @PathVariable("okrId") Optional<UUID> okrId) {
        if (companyId.isPresent()) {
            // Company company = companyService.findById(companyId.get()).get();
            if (buId.isPresent()) {
                // BusinessUnit businessUnit = businessUnitService.findById(buId.get()).get();
                if (okrId.isPresent()) {
                    OKRSet okrSet = okrSetService.findById(okrId.get()).get();
                    List<KeyResult> keyResults = okrSet.getKeyResults();
                    return ResponseEntity.ok(keyResults);
                }
            } else if (okrId.isPresent()) {
                OKRSet okrSet = okrSetService.findById(okrId.get()).get();
                List<KeyResult> keyResults = okrSet.getKeyResults();
                return ResponseEntity.ok(keyResults);
            }
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get a Key Result by ID.
     *
     * @param id The ID of the Key Result.
     * @return The Key Result if found, otherwise returns a not found response.
     */
    @GetMapping("/{id}")
    public ResponseEntity<KeyResult> getKeyResultById(@PathVariable("id") @NonNull UUID id) {
        Optional<KeyResult> keyResult = keyResultService.findById(id);
        if (keyResult.isPresent()) {
            return ResponseEntity.ok(keyResult.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Create a new Key Result.
     *
     * @param keyResult The Key Result to create.
     * @param companyId The ID of the company.
     * @param buId The ID of the business unit.
     * @param okrId The ID of the OKRSet.
     * @return The created Key Result if successful, otherwise returns an unauthorized or conflict response.
     */
    @PostMapping
    public ResponseEntity<KeyResult> createKeyResult(@RequestBody @NonNull KeyResult keyResult,
            @PathVariable("companyId") Optional<UUID> companyId,
            @PathVariable("buId") Optional<UUID> buId, @PathVariable("okrId") Optional<UUID> okrId) {
        if (companyId.isPresent()) {
            Company company = companyService.findById(companyId.get()).get();
            if (buId.isPresent()) {
                BusinessUnit businessUnit = businessUnitService.findById(buId.get()).get();
                if (okrId.isPresent()) {
                    OKRSet okrSet = okrSetService.findById(okrId.get()).get();
                    if (okrSet.getKeyResults().size() < 5) {
                        if (AuthorizationService.isAuthorized(company, businessUnit, okrSet)) {
                            KeyResult createdKeyResult = keyResultService.insert(keyResult);
                            keyResultHistoryService.insert(new KeyResultHistory(createdKeyResult));
                            okrSet.getKeyResults().add(createdKeyResult);
                            okrSetService.save(okrSet);
                            return ResponseEntity.status(HttpStatus.CREATED).body(keyResult);
                        }
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                    }
                    return ResponseEntity.status(HttpStatus.CONFLICT).build();
                }
            } else if (okrId.isPresent()) {
                OKRSet okrSet = okrSetService.findById(okrId.get()).get();
                if (okrSet.getKeyResults().size() < 5) {
                    if (AuthorizationService.isAuthorized(company, null, null)) {
                        KeyResult createdKeyResult = keyResultService.insert(keyResult);
                        keyResultHistoryService.insert(new KeyResultHistory(createdKeyResult));
                        okrSet.getKeyResults().add(createdKeyResult);
                        okrSetService.save(okrSet);
                        return ResponseEntity.status(HttpStatus.CREATED).body(keyResult);
                    }
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Update an existing Key Result.
     *
     * @param id The ID of the Key Result to update.
     * @param keyResult The updated Key Result.
     * @param companyId The ID of the company.
     * @param buId The ID of the business unit.
     * @param okrId The ID of the OKRSet.
     * @return The updated Key Result if successful, otherwise returns an unauthorized or conflict response.
     */
    @PutMapping("/{id}")
    public ResponseEntity<KeyResult> updateKeyResult(@PathVariable("id") UUID id,
            @RequestBody KeyResult keyResult, @PathVariable("companyId") Optional<UUID> companyId,
            @PathVariable("buId") Optional<UUID> buId, @PathVariable("okrId") Optional<UUID> okrId) {
        if (companyId.isPresent()) {
            Company company = companyService.findById(companyId.get()).get();
            if (buId.isPresent()) {
                BusinessUnit businessUnit = businessUnitService.findById(buId.get()).get();
                if (okrId.isPresent()) {
                    OKRSet okrSet = okrSetService.findById(okrId.get()).get();
                    if (AuthorizationService.isAuthorized(company, businessUnit, okrSet)) {
                        UUID oldId = keyResult.getUuid();
                        keyResult.setUuid(id);
                        if (okrSet.getKeyResults().contains(keyResult)) {
                            KeyResult updatedKeyResult = keyResultService.save(keyResult);
                            keyResult.setUuid(oldId);
                            keyResultHistoryService.insert(new KeyResultHistory(keyResult));
                            return ResponseEntity.ok(updatedKeyResult);
                        }
                        return ResponseEntity.status(HttpStatus.CONFLICT).build();
                    }
                }
            } else if (okrId.isPresent()) {
                OKRSet okrSet = okrSetService.findById(okrId.get()).get();
                if (AuthorizationService.isAuthorized(company, null, null)) {
                    UUID oldId = keyResult.getUuid();
                    keyResult.setUuid(id);
                    if (okrSet.getKeyResults().contains(keyResult)) {
                        KeyResult updatedKeyResult = keyResultService.save(keyResult);
                        keyResult.setUuid(oldId);
                        keyResultHistoryService.insert(new KeyResultHistory(keyResult));
                        return ResponseEntity.ok(updatedKeyResult);
                    }
                    return ResponseEntity.status(HttpStatus.CONFLICT).build();
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Delete a Key Result.
     *
     * @param id The ID of the Key Result to delete.
     * @param companyId The ID of the company.
     * @param buId The ID of the business unit.
     * @param okrId The ID of the OKRSet.
     * @return The deleted Key Result if successful, otherwise returns an unauthorized or conflict response.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<KeyResult> deleteKeyResult(@PathVariable("id") UUID id,
            @PathVariable("companyId") Optional<UUID> companyId,
            @PathVariable("buId") Optional<UUID> buId, @PathVariable("okrId") Optional<UUID> okrId) {
        if (companyId.isPresent()) {
            Company company = companyService.findById(companyId.get()).get();
            if (buId.isPresent()) {
                BusinessUnit businessUnit = businessUnitService.findById(buId.get()).get();
                if (okrId.isPresent()) {
                    OKRSet okrSet = okrSetService.findById(okrId.get()).get();
                    if (AuthorizationService.isAuthorized(company, businessUnit, okrSet)) {
                        KeyResult keyResult = keyResultService.findById(id).get();
                        if (okrSet.getKeyResults().contains(keyResult)) {
                            keyResultService.delete(keyResult);
                            okrSet.getKeyResults().remove(keyResult);
                            okrSetService.save(okrSet);
                            return ResponseEntity.ok(keyResult);
                        }
                        return ResponseEntity.status(HttpStatus.CONFLICT).build();
                    }
                }
            } else if (okrId.isPresent()) {
                OKRSet okrSet = okrSetService.findById(okrId.get()).get();
                if (AuthorizationService.isAuthorized(company, null, null)) {
                    KeyResult keyResult = keyResultService.findById(id).get();
                    if (okrSet.getKeyResults().contains(keyResult)) {
                        keyResultService.delete(keyResult);
                        okrSet.getKeyResults().remove(keyResult);
                        okrSetService.save(okrSet);
                        return ResponseEntity.ok(keyResult);
                    }
                    return ResponseEntity.status(HttpStatus.CONFLICT).build();
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }
}
