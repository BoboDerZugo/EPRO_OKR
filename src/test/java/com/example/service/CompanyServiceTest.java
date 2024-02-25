package com.example.service;

import com.example.model.*;
import com.example.model.Unit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.HashSet;
import java.util.Set;


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

        Set<Unit> units = new HashSet<>();
        Set<OKRSet> okrSets = new HashSet<>();
        BusinessUnit businessUnit = new BusinessUnit(units,okrSets);
        Company company = new Company(Set.of(businessUnit),okrSets);
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
        Set<Unit> units = new HashSet<>();
        Set<OKRSet> okrSets = new HashSet<>();
        OKRSet okrSet = new OKRSet();
        BusinessUnit businessUnit = new BusinessUnit(units,okrSets);
        Company company = new Company(Set.of(businessUnit),okrSets);
        company.addOkrSet(okrSet);

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
