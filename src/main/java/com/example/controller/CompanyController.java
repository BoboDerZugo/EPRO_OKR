//Company Controller

package com.example.controller;

import com.example.service.CompanyService;
import com.example.model.Company;
import com.example.model.OKRSet;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

/**
 * The CompanyController class handles the HTTP requests related to the Company entity.
 */
@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    // Crud operations

    /**
     * Retrieves all companies.
     *
     * @return ResponseEntity containing the list of companies
     */
    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        return ResponseEntity.ok(companyService.findAll());
    }

    /**
     * Retrieves a company by its ID.
     *
     * @param id the ID of the company
     * @return ResponseEntity containing the company if found, or a not found response if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable("id") @NonNull UUID id) {
        Optional<Company> company = companyService.findById(id);
        if (company.isPresent()) {
            return ResponseEntity.ok(company.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // /**
    //  * Gets all OKRSets of a company.
    //  * @param id the ID of the company
    //  * @return ResponseEntity containing the OKRSets of the company if found, or a not found response if not found
    //  */
    // @GetMapping("/{id}/okrset")
    // public ResponseEntity<Set<OKRSet>> getOKRSetsByCompany(@PathVariable("id") @NonNull UUID id) {
    //     Optional<Company> company = companyService.findById(id);
    //     if (company.isPresent()) {
    //         return ResponseEntity.ok(company.get().getOkrSets());
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    /**
     * Gets one OKRSet of a company.
     * @param id the ID of the company
     * @param okrSetId the ID of the OKRSet
     * @return ResponseEntity containing the OKRSet of the company if found, or a not found response if not found
     */
    @GetMapping("/{id}/okrset/{okrSetId}")
    public ResponseEntity<OKRSet> getOKRSetByCompany(@PathVariable("id") @NonNull UUID id,
                                                    @PathVariable("okrSetId") @NonNull UUID okrSetId) {
        Optional<Company> company = companyService.findById(id);
        if (company.isPresent()) {
            Company c = company.get();
            for (OKRSet okrSet : c.getOkrSets()) {
                if (okrSet.getUuid().equals(okrSetId)) {
                    return ResponseEntity.ok(okrSet);
                }
            }
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Creates a new company.
     *
     * @param company the company to be created
     * @return ResponseEntity containing the created company if successful, or an internal server error response if not successful
     */
    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody @NonNull Company company) {
        Company createdCompany = companyService.insert(company);
        UUID companyUuid = createdCompany.getUuid();
        if(companyUuid != null) {
            Optional<Company> companyOptional = companyService.findById(companyUuid);
            if (companyOptional.isPresent()) {
                Company c = companyOptional.get();
                if (c != null)
                    return ResponseEntity.status(HttpStatus.CREATED).body(c);
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // // Add OKRSet to Company

    // /**
    //  * Adds an OKRSet to a company.
    //  *
    //  * @param id      the ID of the company
    //  * @param okrSet  the OKRSet to be added
    //  * @return ResponseEntity containing the updated company if successful, or a not found response if the company is not found or a bad request response if the company already has 5 OKRSets
    //  */
    // @PostMapping("/{id}/okrset")
    // public ResponseEntity<Company> addOKRSetToCompany(@PathVariable("id") @NonNull UUID id,
    //                                                   @RequestBody @NonNull OKRSet okrSet) {
    //     Optional<Company> company = companyService.findById(id);
    //     if (company.isPresent()) {
    //         Company c = company.get();
    //         if (c.getOkrSets().size() > 4) {
    //             return ResponseEntity.badRequest().build();
    //         }
    //         c.addOkrSet(okrSet);
    //         c = companyService.save(c);
    //         if(c != null)
    //             return ResponseEntity.ok(c);
    //     }
    //     return ResponseEntity.notFound().build();
    // }

    /**
     * Updates a company.
     *
     * @param id      the ID of the company
     * @param company the updated company
     * @return ResponseEntity containing the updated company if successful, or a not found response if the company is not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable("id") @NonNull UUID id,
                                                 @RequestBody @NonNull Company company) {
        company.setUuid(UUID.fromString(id.toString()));
        Company updatedCompany = companyService.save(company);
        updatedCompany = companyService.findById(id).get();
        //if (updatedCompany != null) {
        return ResponseEntity.ok(updatedCompany);
        // }
        // return ResponseEntity.notFound().build();
    }


    // /**
    //  * Updates an OKRSet of a company.
    //  * @param id the ID of the company
    //  * @param okrSetId the ID of the OKRSet
    //  * @param okrSet the updated OKRSet
    //  * @return ResponseEntity containing the updated company if successful, or a not found response if the company is not found
    //  */ 
    // @PutMapping("/{id}/okrset/{okrSetId}")
    // public ResponseEntity<Company> updateOKRSet(@PathVariable("id") @NonNull UUID id,
    //                                            @PathVariable("okrSetId") @NonNull UUID okrSetId,
    //                                            @RequestBody @NonNull OKRSet okrSet) {
    //     Optional<Company> company = companyService.findById(id);
    //     if (company.isPresent()) {
    //         Company c = company.get();
    //         c.getOkrSets().removeIf(okrSet1 -> okrSet1.getUuid().equals(okrSetId));
    //         okrSet.setUuid(okrSetId);
    //         c.addOkrSet(okrSet);
    //         c = companyService.save(c);
    //         if(c != null)
    //             return ResponseEntity.ok(c);
    //     }
    //     return ResponseEntity.notFound().build();
    // }

    /**
     * Deletes a company.
     *
     * @param id the ID of the company
     * @return ResponseEntity containing the deleted company if successful, or a not found response if the company is not found
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Company> deleteCompany(@PathVariable("id") @NonNull UUID id) {
        Optional<Company> companyToDelete = companyService.deleteByUuid(id);
        if (companyToDelete.isPresent()) {
            return ResponseEntity.ok(companyToDelete.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // /**
    //  * Deletes an OKRSet from a company.
    //  *
    //  * @param id      the ID of the company
    //  * @param okrSetId the ID of the OKRSet
    //  * @return ResponseEntity containing the updated company if successful, or a not found response if the company is not found
    //  */
    // @DeleteMapping("/{id}/okrset/{okrSetId}")
    // public ResponseEntity<Company> deleteOKRSet(@PathVariable("id") @NonNull UUID id,
    //                                            @PathVariable("okrSetId") @NonNull UUID okrSetId) {
    //     Optional<Company> company = companyService.findById(id);
    //     if (company.isPresent()) {
    //         Company c = company.get();
    //         c.getOkrSets().removeIf(okrSet -> okrSet.getUuid().equals(okrSetId));
    //         c = companyService.save(c);
    //         if(c != null)
    //             return ResponseEntity.ok(c);
    //     }
    //     return ResponseEntity.notFound().build();
    // }
}
