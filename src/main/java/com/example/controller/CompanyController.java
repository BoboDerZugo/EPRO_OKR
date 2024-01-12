//Company Controller

package com.example.controller;

import com.example.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService cS){
        this.companyService = cS;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String getCompanys(){
        return companyService.getCompanies();
    }

    @GetMapping("/name/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String getCompanyName(){
        return companyService.getCompanyName();
    }

    @GetMapping("/{id}/businessunit")
    @ResponseStatus(HttpStatus.OK)
    public String getBusinessUnitsByCompanyId(@PathVariable("id") Long id){
        return companyService.getBusinessUnitsByCompanyId();
    }

    @GetMapping("/{id}/okr")
    @ResponseStatus(HttpStatus.OK)
    public String getOKRsByCompanyId(@PathVariable("id") Long id){
        return companyService.getOKRsByCompanyId();
    }

    @GetMapping("/{id}/employees")
    @ResponseStatus(HttpStatus.OK)
    public String getEmployeesByCompanyId(@PathVariable("id") Long id){
        return companyService.getEmployeesByCompanyId();
    }


    
}
