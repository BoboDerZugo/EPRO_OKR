//Company Controller

package com.example.controller;

import com.example.service.CompanyService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.model.*;

@RestController
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    //Crud operations
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Company> getAllCompanies(){
        return companyService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Company getCompanyById(@PathVariable("id") Long id){
        return companyService.findById(id);
    }   


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company createCompany(@RequestBody Company company){
        return companyService.insertOne(company);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Company updateCompany(@PathVariable("id") Long id, @RequestBody Company company){
        return companyService.updateOne(id, company);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCompany(@PathVariable("id") Long id){
        companyService.deleteOne(id);
    }
}
