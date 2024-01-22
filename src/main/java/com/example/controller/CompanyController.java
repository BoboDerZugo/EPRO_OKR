//Company Controller

package com.example.controller;

import com.example.service.CompanyService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import com.example.model.*;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    //Crud operations
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Company> getAllCompanies(){
        return companyService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Company getCompanyById(@PathVariable("id") @NonNull UUID id){
        Optional<Company> company = companyService.findById(id);
        return company.get();
    }   


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company createCompany(@RequestBody @NonNull Company company){
        return companyService.insert(company);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Company updateCompany(@PathVariable("id") @NonNull UUID id, @RequestBody Company company){
        Optional<Company> companyToUpdate = companyService.updateOne(id, company);
        return companyToUpdate.get();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Company deleteCompany(@PathVariable("id") @NonNull Long id){
        Optional<Company> companyToDelete = companyService.delete(id);
        return companyToDelete.get();
    }
}
