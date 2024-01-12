package com.example.service;

import com.example.model.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface CompanyService extends MongoRepository<Company, UUID> {


    String getCompanies();

    String getCompanyName();

    String getBusinessUnitsByCompanyId();

    String getOKRsByCompanyId();

    String getEmployeesByCompanyId();
}
