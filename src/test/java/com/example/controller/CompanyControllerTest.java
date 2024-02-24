package com.example.controller;

import com.example.model.Company;
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

@SpringBootTest
public class CompanyControllerTest {

    @Mock
    private CompanyService companyService;

    @InjectMocks
    private CompanyController companyController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCompanies() {
        // Arrange
        List<Company> companies = Arrays.asList(new Company(), new Company());
        when(companyService.findAll()).thenReturn(companies);

        // Act
        ResponseEntity<List<Company>> response = companyController.getAllCompanies();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(companies);
    }

    @Test
    public void testGetCompanyById_CompanyFound() {
        // Arrange
        UUID companyId = UUID.randomUUID();
        Company company = new Company();
        company.setUuid(companyId);
        when(companyService.findById(companyId)).thenReturn(Optional.of(company));

        // Act
        ResponseEntity<Company> response = companyController.getCompanyById(companyId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(company);
    }

    @Test
    public void testGetCompanyById_CompanyNotFound() {
        // Arrange
        UUID companyId = UUID.randomUUID();
        when(companyService.findById(companyId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Company> response = companyController.getCompanyById(companyId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void testCreateCompany() {
        // Arrange
        Company company = new Company();
        UUID companyId = UUID.randomUUID();
        company.setUuid(companyId);
        when(companyService.insert(company)).thenReturn(company);
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));

        // Act
        ResponseEntity<Company> response = companyController.createCompany(company);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(company);
    }

    @Test
    public void testUpdateCompany_CompanyFound() {
        // Arrange
        UUID companyId = UUID.randomUUID();
        Company company = new Company();
        company.setUuid(companyId);
        when(companyService.save(company)).thenReturn(company);
        when(companyService.findById(companyId)).thenReturn(Optional.of(company));

        // Act
        ResponseEntity<Company> response = companyController.updateCompany(companyId, company);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(company);
    }

    @Test
    public void testUpdateCompany_CompanyNotFound() {
        // Arrange
        UUID companyId = UUID.randomUUID();
        Company company = new Company();
        company.setUuid(companyId);
        when(companyService.findById(companyId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Company> response = companyController.updateCompany(companyId, company);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void testDeleteCompany_CompanyFound() {
        // Arrange
        UUID companyId = UUID.randomUUID();
        Company company = new Company();
        company.setUuid(companyId);
        when(companyService.deleteByUuid(companyId)).thenReturn(Optional.of(company));

        // Act
        ResponseEntity<Company> response = companyController.deleteCompany(companyId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(company);
    }

    @Test
    public void testDeleteCompany_CompanyNotFound() {
        // Arrange
        UUID companyId = UUID.randomUUID();
        when(companyService.deleteByUuid(companyId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Company> response = companyController.deleteCompany(companyId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }
}