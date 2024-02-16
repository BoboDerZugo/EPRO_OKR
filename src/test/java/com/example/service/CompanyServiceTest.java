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
    public void CompanyServiceTest_findByBusinessUnitsContains(){
        //arrange
        User user = new User("John Doe","NORMAL");
        Set<Unit> unitSet = new HashSet<>();
        KeyResult keyResult = new KeyResult("Keys",(short)2,0.2,1.0,0.9,user,"Lorem Ipsum","Ongoing");
        Objective objective = new Objective("Objective",(short)4);
        OKRSet[] okrSet = {new OKRSet(objective,keyResult)};
        keyResult.setOkrSet(okrSet[0]);

        Unit unit = new Unit(Set.of(user));


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
    public void CompanyServiceTest_findByOkrSetsContains(){
        //arrange
        User user = new User("John Doe","NORMAL");
        Set<Unit> unitSet = new HashSet<>();
        KeyResult keyResult = new KeyResult("Keys",(short)2,0.2,1.0,0.9,user,"Lorem Ipsum","Ongoing");
        Objective objective = new Objective("Objective",(short)4);
        OKRSet okrSet = new OKRSet(objective,keyResult);

        Company company = new Company();
        company.addOKRSet(okrSet);
        companyService.save(company);

        //act
        Company savedCompany = companyService.findByOkrSetsContains(okrSet).get();
        //assert
        Assertions.assertThat(savedCompany).isNotNull();
        Assertions.assertThat(savedCompany.getUuid()).isEqualByComparingTo(savedCompany.getUuid());
    }

    @Test
    public void CompanyServiceTest_deleteByUuid(){
        //arrange
        Company company = new Company();
        companyService.save(company);
        //act
        companyService.deleteByUuid(company.getUuid());
        //assert
        Assertions.assertThat(companyService.findById(company.getUuid())).isEmpty();
    }


}
