package com.example.controller;

import com.example.model.BusinessUnit;
import com.example.model.Company;
import com.example.model.OKRSet;
import com.example.model.Unit;
import com.example.service.BusinessUnitService;
import com.example.service.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import java.util.HashSet;

@SpringBootTest
public class BusinessUnitControllerTest {

    @Mock
    private BusinessUnitService businessUnitService;

    @Mock
    private CompanyService companyService;

    @InjectMocks
    private BusinessUnitController businessUnitController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllBusinessUnits() {
        // Arrange
        UUID companyId = UUID.randomUUID();
        // ...

        OKRSet okrSet[] = new OKRSet[1];
        okrSet[0] = new OKRSet();
        BusinessUnit businessUnit1 = new BusinessUnit(okrSet);
        BusinessUnit businessUnit2 = new BusinessUnit(okrSet);
        Set<BusinessUnit> businessUnits = new HashSet<>(Arrays.asList(businessUnit1, businessUnit2));
        Set<OKRSet> okrSets = new HashSet<>(Arrays.asList(okrSet));
        Company company = new Company(businessUnits, okrSets);

        when(companyService.findById(companyId)).thenReturn(Optional.of(company));
        // Act
        ResponseEntity<Set<BusinessUnit>> response = businessUnitController.getAllBusinessUnits(Optional.of(companyId));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(businessUnits);
    }

    @Test
    public void testGetAllBusinessUnits_CompanyNotFound() {
        // Arrange
        UUID companyId = UUID.randomUUID();

        when(companyService.findById(companyId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Set<BusinessUnit>> response = businessUnitController.getAllBusinessUnits(Optional.of(companyId));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void testGetUnitsByBusinessUnitId() {
        // Arrange
        UUID businessUnitId = UUID.randomUUID();
        OKRSet okrSet[] = new OKRSet[1];
        okrSet[0] = new OKRSet();
        Set<Unit> units = new HashSet<>(Arrays.asList(new Unit(), new Unit()));
        Set<OKRSet> okrSets = new HashSet<>(Arrays.asList(okrSet));
        BusinessUnit businessUnit = new BusinessUnit(units, okrSets);

        when(businessUnitService.findById(businessUnitId)).thenReturn(Optional.of(businessUnit));

        // Act
        ResponseEntity<Set<Unit>> response = businessUnitController.getUnitsByBusinessUnitId(businessUnitId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(units);
    }

    @Test
    public void testGetUnitsByBusinessUnitId_BusinessUnitNotFound() {
        // Arrange
        UUID businessUnitId = UUID.randomUUID();

        when(businessUnitService.findById(businessUnitId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Set<Unit>> response = businessUnitController.getUnitsByBusinessUnitId(businessUnitId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void testGetBusinessUnitById() {
        // Arrange
        UUID businessUnitId = UUID.randomUUID();
        OKRSet okrSet[] = new OKRSet[1];
        okrSet[0] = new OKRSet();
        Set<Unit> units = new HashSet<>(Arrays.asList(new Unit(), new Unit()));
        Set<OKRSet> okrSets = new HashSet<>(Arrays.asList(okrSet));
        BusinessUnit businessUnit = new BusinessUnit(units, okrSets);

        when(businessUnitService.findById(businessUnitId)).thenReturn(Optional.of(businessUnit));

        // Act
        ResponseEntity<BusinessUnit> response = businessUnitController.getBusinessUnitById(businessUnitId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(businessUnit);
    }

    @Test
    public void testGetBusinessUnitById_BusinessUnitNotFound() {
        // Arrange
        UUID businessUnitId = UUID.randomUUID();

        when(businessUnitService.findById(businessUnitId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<BusinessUnit> response = businessUnitController.getBusinessUnitById(businessUnitId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void testCreateBusinessUnit() {
        // Arrange
        OKRSet okrSet[] = new OKRSet[1];
        okrSet[0] = new OKRSet();
        Set<Unit> units = new HashSet<>(Arrays.asList(new Unit(), new Unit()));
        Set<OKRSet> okrSets = new HashSet<>(Arrays.asList(okrSet));
        BusinessUnit businessUnit = new BusinessUnit(units, okrSets);
        Set<BusinessUnit> businessUnits = new HashSet<>(Arrays.asList(businessUnit));
        Company company = new Company(businessUnits, okrSets);
        UUID companyId = UUID.randomUUID();

        when(companyService.findById(companyId)).thenReturn(Optional.of(company));

        when(businessUnitService.save(businessUnit)).thenReturn(businessUnit);

        // Act
        ResponseEntity<BusinessUnit> response = businessUnitController.createBusinessUnit(businessUnit,
                Optional.of(companyId));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(businessUnit);
    }

    @Test
    public void testCreateBusinessUnit_CompanyNotFound() {
        // Arrange
        OKRSet okrSet[] = new OKRSet[1];
        okrSet[0] = new OKRSet();
        Set<Unit> units = new HashSet<>(Arrays.asList(new Unit(), new Unit()));
        Set<OKRSet> okrSets = new HashSet<>(Arrays.asList(okrSet));
        BusinessUnit businessUnit = new BusinessUnit(units, okrSets);
        UUID companyId = UUID.randomUUID();

        when(companyService.findById(companyId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<BusinessUnit> response = businessUnitController.createBusinessUnit(businessUnit,
                Optional.of(companyId));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void testUpdateBusinessUnit() {
        // Arrange
        UUID businessUnitId = UUID.randomUUID();
        OKRSet okrSet[] = new OKRSet[1];
        okrSet[0] = new OKRSet();
        Set<Unit> units = new HashSet<>(Arrays.asList(new Unit(), new Unit()));
        Set<OKRSet> okrSets = new HashSet<>(Arrays.asList(okrSet));
        BusinessUnit businessUnit = new BusinessUnit(units, okrSets);
        Set<BusinessUnit> businessUnits = new HashSet<>(Arrays.asList(businessUnit));
        UUID companyId = UUID.randomUUID();
        Company company = new Company(businessUnits, okrSets);
        businessUnit.setUuid(businessUnitId);
        company.addBusinessUnit(businessUnit);

        when(businessUnitService.findById(businessUnitId)).thenReturn(Optional.of(businessUnit));
        when(companyService.findById(companyId)).thenReturn(Optional.of(company));
        when(businessUnitService.save(businessUnit)).thenReturn(businessUnit);

        // Act
        ResponseEntity<BusinessUnit> response = businessUnitController.updateBusinessUnit(businessUnitId, businessUnit,
                Optional.of(companyId));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(businessUnit);
    }

    @Test
    public void testUpdateBusinessUnit_CompanyNotFound() {
        // Arrange
        UUID businessUnitId = UUID.randomUUID();
        BusinessUnit businessUnit = new BusinessUnit(null, null);
        UUID companyId = UUID.randomUUID();

        when(businessUnitService.findById(businessUnitId)).thenReturn(Optional.of(businessUnit));
        when(companyService.findById(companyId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<BusinessUnit> response = businessUnitController.updateBusinessUnit(businessUnitId, businessUnit,
                Optional.of(companyId));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void testUpdateBusinessUnit_BusinessUnitNotInCompany() {
        // Arrange
        UUID businessUnitId = UUID.randomUUID();
        OKRSet okrSet[] = new OKRSet[1];
        okrSet[0] = new OKRSet();
        Set<Unit> units = new HashSet<>(Arrays.asList(new Unit(), new Unit()));
        Set<OKRSet> okrSets = new HashSet<>(Arrays.asList(okrSet));
        BusinessUnit businessUnit = new BusinessUnit(units, okrSets);
        Set<BusinessUnit> businessUnits = new HashSet<>();
        UUID companyId = UUID.randomUUID();
        Company company = new Company(businessUnits, okrSets);
        businessUnit.setUuid(businessUnitId);

        when(businessUnitService.findById(businessUnitId)).thenReturn(Optional.of(businessUnit));
        when(companyService.findById(companyId)).thenReturn(Optional.of(company));
        when(businessUnitService.save(businessUnit)).thenReturn(businessUnit);

        // Act
        ResponseEntity<BusinessUnit> response = businessUnitController.updateBusinessUnit(businessUnitId, businessUnit,
                Optional.of(companyId));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void testDeleteBusinessUnit() {
        // Arrange
        UUID businessUnitId = UUID.randomUUID();
        OKRSet okrSet[] = new OKRSet[1];
        okrSet[0] = new OKRSet();
        Set<Unit> units = new HashSet<>(Arrays.asList(new Unit(), new Unit()));
        Set<OKRSet> okrSets = new HashSet<>(Arrays.asList(okrSet));
        BusinessUnit businessUnit = new BusinessUnit(units, okrSets);
        Set<BusinessUnit> businessUnits = new HashSet<>(Arrays.asList(businessUnit));
        UUID companyId = UUID.randomUUID();
        Company company = new Company(businessUnits, okrSets);
        businessUnit.setUuid(businessUnitId);
        company.addBusinessUnit(businessUnit);

        when(businessUnitService.findById(businessUnitId)).thenReturn(Optional.of(businessUnit));
        when(companyService.findById(companyId)).thenReturn(Optional.of(company));

        // Act
        ResponseEntity<BusinessUnit> response = businessUnitController.deleteBusinessUnit(businessUnitId,
                Optional.of(companyId));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(businessUnit);
    }

    @Test
    public void testDeleteBusinessUnit_CompanyNotFound() {
        // Arrange
        UUID businessUnitId = UUID.randomUUID();
        BusinessUnit businessUnit = new BusinessUnit(null, null);
        UUID companyId = UUID.randomUUID();

        when(businessUnitService.findById(businessUnitId)).thenReturn(Optional.of(businessUnit));
        when(companyService.findById(companyId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<BusinessUnit> response = businessUnitController.deleteBusinessUnit(businessUnitId,
                Optional.of(companyId));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void testDeleteBusinessUnit_BusinessUnitNotInCompany() {
        // Arrange
        UUID businessUnitId = UUID.randomUUID();
        OKRSet okrSet[] = new OKRSet[1];
        okrSet[0] = new OKRSet();
        Set<Unit> units = new HashSet<>(Arrays.asList(new Unit(), new Unit()));
        Set<OKRSet> okrSets = new HashSet<>(Arrays.asList(okrSet));
        BusinessUnit businessUnit = new BusinessUnit(units, okrSets);
        Set<BusinessUnit> businessUnits = new HashSet<>();
        UUID companyId = UUID.randomUUID();
        Company company = new Company(businessUnits, okrSets);
        businessUnit.setUuid(businessUnitId);

        when(businessUnitService.findById(businessUnitId)).thenReturn(Optional.of(businessUnit));
        when(companyService.findById(companyId)).thenReturn(Optional.of(company));

        // Act
        ResponseEntity<BusinessUnit> response = businessUnitController.deleteBusinessUnit(businessUnitId,
                Optional.of(companyId));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }
}