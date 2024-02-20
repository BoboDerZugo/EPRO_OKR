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
     * 
     * @param companyId
     * @param buId
     * @param okrId
     * @throws IllegalArgumentException if the company, business unit, or OKRSet is
     *                                  not found.
     * @return A list of Key Results if successful, otherwise returns a not found
     *         response.
     */
    @GetMapping
    public ResponseEntity<List<KeyResult>> getAllKeyResults(
            @PathVariable("companyId") @NonNull Optional<UUID> companyId,
            @PathVariable("buId") Optional<UUID> buId, @PathVariable("okrId") @NonNull Optional<UUID> okrId) {
        if (companyId.isPresent()) {
            if (buId.isPresent()) {
                if (okrId.isPresent()) {
                    OKRSet okrSet = okrSetService.findById(okrId.get())
                            .orElse(null);
                    if (okrSet == null) {
                        return ResponseEntity.notFound().build();
                    }
                    List<KeyResult> keyResults = okrSet.getKeyResults();
                    return ResponseEntity.ok(keyResults);
                }
            } else if (okrId.isPresent()) {
                OKRSet okrSet = okrSetService.findById(okrId.get())
                        .orElse(null);
                if (okrSet == null) {
                    return ResponseEntity.notFound().build();
                }
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
     * @param buId      The ID of the business unit.
     * @param okrId     The ID of the OKRSet.
     * @throws IllegalArgumentException if the company, business unit, or OKRSet is
     *                                  not found.
     * @return The created Key Result if successful, otherwise returns an
     *         unauthorized or conflict response.
     */
    @PostMapping
    public ResponseEntity<KeyResult> createKeyResult(@RequestBody @NonNull KeyResult keyResult,
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
                KeyResult createdKeyResult = keyResultService.insert(keyResult);
                if (!okrSet.addKeyResult(createdKeyResult)) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).build();
                }
                okrSetService.save(okrSet);
                return ResponseEntity.status(HttpStatus.CREATED).body(createdKeyResult);
            }
            // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Update an existing Key Result.
     *
     * @param id        The ID of the Key Result to update.
     * @param keyResult The updated Key Result.
     * @param companyId The ID of the company.
     * @param buId      The ID of the business unit.
     * @param okrId     The ID of the OKRSet.
     * @throws IllegalArgumentException if the company, business unit, or OKRSet is
     *                                  not found.
     * @return The updated Key Result if successful, otherwise returns an
     *         unauthorized or conflict response.
     */
    @PutMapping("/{id}")
    public ResponseEntity<KeyResult> updateKeyResult(@PathVariable("id") @NonNull UUID id,
            @RequestBody @NonNull KeyResult keyResult, @PathVariable("companyId") @NonNull Optional<UUID> companyId,
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
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Delete a Key Result.
     *
     * @param id        The ID of the Key Result to delete.
     * @param companyId The ID of the company.
     * @param buId      The ID of the business unit.
     * @param okrId     The ID of the OKRSet.
     * @throws IllegalArgumentException if the company, business unit, or OKRSet is
     *                                  not found.
     * @return The deleted Key Result if successful, otherwise returns an
     *         unauthorized or conflict response.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<KeyResult> deleteKeyResult(@PathVariable("id") @NonNull UUID id,
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
                KeyResult keyResult = keyResultService.findById(id)
                        .orElse(null);
                if (keyResult == null) {
                    return ResponseEntity.notFound().build();
                }
                if (okrSet.getKeyResults().contains(keyResult)) {
                    keyResultService.delete(keyResult);
                    okrSet.getKeyResults().remove(keyResult);
                    okrSetService.save(okrSet);
                    return ResponseEntity.ok(keyResult);
                }
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }

}
