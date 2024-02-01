//Company Controller

package com.example.controller;

import com.example.service.CompanyService;
import com.example.model.Company;
import com.example.model.OKRSet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    // Crud operations
    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        return ResponseEntity.ok(companyService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable("id") @NonNull UUID id) {
        Optional<Company> company = companyService.findById(id);
        if (company.isPresent()) {
            return ResponseEntity.ok(company.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody @NonNull Company company) {
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.insert(company));
    }

    // Add OKRSet to Company
    @PostMapping("/{id}/okrset")
    public ResponseEntity<Company> addOKRSetToCompany(@PathVariable("id") @NonNull UUID id,
            @RequestBody @NonNull OKRSet okrSet) {
        Optional<Company> company = companyService.findById(id);
        if (company.isPresent()) {
            Company c = company.get();
            if (c.getOkrSets().size() > 4) {
                return ResponseEntity.badRequest().build();
            }
            c.addOKRSet(okrSet);
            company = companyService.updateOne(id, c);
            if (company.isPresent()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(company.get());
            }
        }
        return ResponseEntity.notFound().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable("id") @NonNull UUID id,
            @RequestBody Company company) {
        Optional<Company> companyToUpdate = companyService.updateOne(id, company);
        if (companyToUpdate.isPresent()) {
            return ResponseEntity.ok(companyToUpdate.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Company> deleteCompany(@PathVariable("id") @NonNull UUID id) {
        Optional<Company> companyToDelete = companyService.delete(id);
        if (companyToDelete.isPresent()) {
            return ResponseEntity.ok(companyToDelete.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
