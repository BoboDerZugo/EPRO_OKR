//Company Controller

package com.example.controller;

import com.example.service.CompanyService;
import com.example.model.Company;
import com.example.model.OKRSet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    // // ??
    // // Aggregated OKR
    // @GetMapping("/{id}/aggregatedOKR")
    // public ResponseEntity<String> getAggregatedOKR(@PathVariable("id") @NonNull UUID id) {
    //     Optional<Company> company = companyService.findById(id);
    //     if (company.isPresent()) {
    //         Company c = company.get();
    //         // JSON String
    //         /*
    //          * {
    //          * "company": {
    //          * "name": "name",
    //          * "okrSets": [
    //          * {
    //          * "objective": {
    //          * "name": "name",
    //          * "fullfilled": "fullfilled"
    //          * }
    //          * "keyResults": [
    //          * {
    //          * "description": "description",
    //          * "fullfillment": "fullfillment",
    //          * "goal": "goal",
    //          * "current": "current",
    //          * "OKRSet": { (optional)
    //          * "objective": {
    //          * "name": "name",
    //          * "fullfilled": "fullfilled"
    //          * }
    //          * "keyResults": [ .......
    //          * 
    //          * }
    //          * },
    //          * {
    //          * "description": "description",
    //          * "fullfillment": "fullfillment",
    //          * "goal": "goal",
    //          * "current": "current",
    //          * "OKRSet": { (optional)
    //          * "objective": {
    //          * "name": "name",
    //          * "fullfilled": "fullfilled"
    //          * }
    //          * "keyResults": [ .......
    //          * }
    //          * }
    //          * ]
    //          * },
    //          * {
    //          * "objective": {
    //          * "name": "name",
    //          * "fullfilled": "fullfilled"
    //          * }
    //          * "keyResults": [
    //          * {
    //          * "description": "description",
    //          * "fullfillment": "fullfillment",
    //          * "goal": "goal",
    //          * "current": "current",
    //          * "OKRSet": { (optional)
    //          * "objective": {
    //          * "name": "name",
    //          * "fullfilled": "fullfilled"
    //          * }
    //          * "keyResults": [ .......
    //          * }
    //          * },
    //          * {
    //          * "description": "description",
    //          * "fullfillment": "fullfillment",
    //          * "goal": "goal",
    //          * "current": "current",
    //          * "OKRSet": { (optional)
    //          * "objective": {
    //          * "name": "name",
    //          * "fullfilled": "fullfilled"
    //          * }
    //          * "keyResults": [ .......
    //          * }
    //          * }
    //          * ]
    //          * }
    //          * ]
    //          * }
    //          * }
    //          * 
    //          */
    //         String aggregatedOKR = "{\"company\": {\"name\": \"" + "-" + "\", \"okrSets\": ["
    //                 + c.getOkrSets().stream().map(okrSet -> "{\"objective\": {\"name\": \""
    //                         + okrSet.getObjective().getName() + "\", \"fullfilled\": \"" +
    //                         okrSet.getObjective().getFulfilled() + "\"}, \"keyResults\": ["
    //                         + okrSet.getKeyResults().stream().map(kr -> "{\"description\": \"" + kr.getDescription()
    //                                 + "\", \"fullfillment\": \"" + kr.getFulfilled() + "\", \"goal\": \"" +
    //                                 kr.getGoal() + "\", \"current\": \"" + kr.getCurrent() + "\", \"OKRSet\": {"
    //                                 + "\"objective\": {\"name\": \"" + okrSet.getObjective().getName()
    //                                 + "\", \"fullfilled\": \"" +
    //                                 okrSet.getObjective().getFulfilled() + "\"}, \"keyResults\": ["
    //                                 + okrSet.getKeyResults().stream()
    //                                         .map(kr2 -> "{\"description\": \"" + kr2.getDescription()
    //                                                 + "\", \"fullfillment\": \"" + kr2.getFulfilled()
    //                                                 + "\", \"goal\": \"" +
    //                                                 kr2.getGoal() + "\", \"current\": \"" + kr2.getCurrent() + "\"}")
    //                                         .collect(Collectors.joining(","))
    //                                 + "]}}").collect(Collectors.joining(","))
    //                         + "]}").collect(Collectors.joining(","))
    //                 + "]}";

    //         return ResponseEntity.ok(aggregatedOKR);
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody @NonNull Company company) {
        Company createdCompany = companyService.insert(company);
        Optional<Company> companyOptional = companyService.findById(createdCompany.getUuid());
        if (companyOptional.isPresent()) {
            Company c = companyOptional.get();
            if (c != null)
                return ResponseEntity.status(HttpStatus.CREATED).body(c);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
            c = companyService.save(c);
            if(c != null)
                return ResponseEntity.ok(c);
        }
        return ResponseEntity.notFound().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable("id") @NonNull UUID id,
            @RequestBody @NonNull Company company) {
        company.setUuid(UUID.fromString(id.toString()));
        Company updatedCompany = companyService.save(company);
        if (updatedCompany != null) {
            return ResponseEntity.ok(updatedCompany);
        }
        return ResponseEntity.notFound().build();

    }

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
}
