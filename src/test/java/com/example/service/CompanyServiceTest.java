package com.example.service;

import com.example.model.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.HashSet;
import java.util.Set;


/*
    Optional<Company> findByBusinessUnitsContains(BusinessUnit businessUnit);
    Optional<Company> findByOkrSetsContains(OKRSet okrSet);
    Optional<Company> findByEmployeeSetContains(User user);
 */
@DataMongoTest
@AutoConfigureDataMongo
public class CompanyServiceTest {
    @Autowired
    CompanyService companyService;

    @Test
    public void CompanyServiceTest_save(){
        //arrange

        Company company = new Company();

        //act
        Company savedCompany = companyService.save(company);

        //assert
        Assertions.assertThat(savedCompany).isNotNull();
        Assertions.assertThat(savedCompany.getUuid()).isEqualByComparingTo(company.getUuid());
    }

    @Test
    public void BusinessUnitServiceTest_findByBusinessUnitsContains(){
        //arrange
        User user = new User("John Doe","NORMAL");
        Set<Unit> unitSet = new HashSet<>();
        KeyResult keyResult = new KeyResult("Keys",(short)2,0.2,1.0,0.9,user,"Lorem Ipsum","Ongoing",unitSet);
        Objective objective = new Objective("Objective",(short)4);
        OKRSet[] okrSet = {new OKRSet(objective,keyResult)};
        Unit unit = new Unit(okrSet);


        BusinessUnit businessUnit = new BusinessUnit(okrSet);
        businessUnit.addUnit(unit);

        Company company = new Company();
        company.addBusinessUnit(businessUnit);
        companyService.save(company);

        //act
        Company savedCompany = companyService.findByBusinessUnitsContains(businessUnit).get();
        //assert
        Assertions.assertThat(savedCompany).isNotNull();
        Assertions.assertThat(savedCompany.getUuid()).isEqualByComparingTo(company.getUuid());
    }
    @Test
    public void BusinessUnitServiceTest_findByOkrSetsContains(){
        //arrange
        User user = new User("John Doe","NORMAL");
        Set<Unit> unitSet = new HashSet<>();
        KeyResult keyResult = new KeyResult("Keys",(short)2,0.2,1.0,0.9,user,"Lorem Ipsum","Ongoing",unitSet);
        Objective objective = new Objective("Objective",(short)4);
        OKRSet okrSet = new OKRSet(objective,keyResult);

        Company company = new Company();
        company.addOkrSet(okrSet);
        companyService.save(company);

        //act
        Company savedCompany = companyService.findByOkrSetsContains(okrSet).get();
        //assert
        Assertions.assertThat(savedCompany).isNotNull();
        Assertions.assertThat(savedCompany.getUuid()).isEqualByComparingTo(savedCompany.getUuid());
    }
    @Test
    public void BusinessUnitServiceTest_findByEmployeeSetContains(){
        //arrange
        User user = new User("John Doe","NORMAL");

        Company company = new Company();
        company.addEmployee(user);
        companyService.save(company);

        //act
        Company savedCompany = companyService.findByEmployeeSetContains(user).get();
        //assert
        Assertions.assertThat(savedCompany).isNotNull();
        Assertions.assertThat(savedCompany.getUuid()).isEqualByComparingTo(company.getUuid());
    }
}
