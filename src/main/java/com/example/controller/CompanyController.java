//Company Controller

package com.example.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;

import com.example.model.CompanyModel;
import com.example.service.CompanyService;

@RestController
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String getCompanys(){
        return CompanyService.getCompanys();
    }

    @GetMapping("/name/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String getCompanyName(){
        return CompanyService.getCompanyName();
    }

    @GetMapping("/{id}/businessunit")
    @ResponseStatus(HttpStatus.OK)
    public String getBusinessUnitsByCompanyId(@PathVariable("id") Long id){
        return CompanyService.getBusinessUnitsByCompanyId();
    }

    @GetMapping("/{id}/okr")
    @ResponseStatus(HttpStatus.OK)
    public String getOKRsByCompanyId(@PathVariable("id") Long id){
        return CompanyService.getOKRsByCompanyId();
    }

    @GetMapping("/{id}/employees")
    @ResponseStatus(HttpStatus.OK)
    public String getEmployeesByCompanyId(@PathVariable("id") Long id){
        return CompanyService.getEmployeesByCompanyId();
    }


    
}
