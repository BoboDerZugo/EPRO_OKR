package com.example.controller;

import com.example.model.BusinessUnit;
import com.example.model.Company;
import com.example.model.KeyResult;
import com.example.model.OKRSet;
import com.example.model.Objective;
import com.example.model.Unit;
import com.example.model.User;
import com.example.service.BusinessUnitService;
import com.example.service.CompanyService;
import com.example.service.KeyResultHistoryService;
import com.example.service.KeyResultService;
import com.example.service.OKRSetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class KeyResultControllerTest {

    @Mock
    private KeyResultService keyResultService;

    @Mock
    private KeyResultHistoryService keyResultHistoryService;

    @Mock
    private CompanyService companyService;

    @Mock
    private OKRSetService okrSetService;

    @Mock
    private BusinessUnitService businessUnitService;

    @InjectMocks
    private KeyResultController keyResultController;

    Objective objective;
    KeyResult keyResult;
    OKRSet okrSet;
    Set<User> users;
    Unit unit;
    User user;
    BusinessUnit businessUnit;
    Company company;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        objective = new Objective("testObjective", (short) 10);
        objective.setUuid(UUID.randomUUID());
        keyResult = new KeyResult();
        keyResult.setUuid(UUID.randomUUID());
        okrSet = new OKRSet(objective, keyResult);
        okrSet.setUuid(UUID.randomUUID());
        user = new User("testAdmin1", "password", "BU_ADMIN");
        user.setUuid(UUID.fromString("87559ff1-ae30-4af5-9ba2-1723bed1f706"));
        Set<User> users = new HashSet<>();
        users.add(user);
        unit = new Unit(users);
        unit.setUuid(UUID.randomUUID());
        businessUnit = new BusinessUnit(new HashSet<Unit>(Arrays.asList(unit)),
                new HashSet<OKRSet>(Arrays.asList(okrSet)));
        businessUnit.setUuid(UUID.randomUUID());
        company = new Company(new HashSet<BusinessUnit>(Arrays.asList(businessUnit)),
                new HashSet<OKRSet>(Arrays.asList(okrSet)));
        company.setUuid(UUID.randomUUID());
    }

    @Test
    public void testGetAllKeyResults_CompanyBuOkrFound() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));

        // Act
        ResponseEntity<List<KeyResult>> response = keyResultController.getAllKeyResults(Optional.of(company.getUuid()),
                Optional.of(businessUnit.getUuid()), Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(okrSet.getKeyResults());
    }

    @Test
    public void testGetAllKeyResults_CompanyOkrFound() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));

        // Act
        ResponseEntity<List<KeyResult>> response = keyResultController.getAllKeyResults(Optional.of(company.getUuid()),
                Optional.empty(), Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(okrSet.getKeyResults());
    }

    @Test
    public void testGetAllKeyResults_CompanyOkrNotFound() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<List<KeyResult>> response = keyResultController.getAllKeyResults(Optional.of(company.getUuid()),
                Optional.empty(), Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void testGetAllKeyResults_BusinessUnitOkrNotFound() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<List<KeyResult>> response = keyResultController.getAllKeyResults(Optional.of(company.getUuid()),
                Optional.of(businessUnit.getUuid()), Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void testGetKeyResultById_KeyResultFound() {
        // Arrange
        when(keyResultService.findById(keyResult.getUuid())).thenReturn(Optional.of(keyResult));

        // Act
        ResponseEntity<KeyResult> response = keyResultController.getKeyResultById(keyResult.getUuid());

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(keyResult);
    }

    @Test
    public void testGetKeyResultById_KeyResultNotFound() {
        // Arrange
        when(keyResultService.findById(keyResult.getUuid())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<KeyResult> response = keyResultController.getKeyResultById(keyResult.getUuid());

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    // Test for createKeyResult
    // Should return 201 and the created key result
    @Test
    @WithMockCustomUser(username = "testAdmin1", roles = { "BU_ADMIN" }, password = "password", value = "testAdmin1")
    public void testCreateKeyResult_BusinessUnit() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));
        when(keyResultService.insert(keyResult)).thenReturn(keyResult);

        ResponseEntity<KeyResult> response = keyResultController.createKeyResult(keyResult,
                Optional.of(company.getUuid()), Optional.of(businessUnit.getUuid()), Optional.of(okrSet.getUuid()));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(keyResult);
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", roles = { "CO_ADMIN" }, password = "password", value = "testAdmin1")
    public void testCreateKeyResult_Company() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));
        when(keyResultService.insert(keyResult)).thenReturn(keyResult);

        ResponseEntity<KeyResult> response = keyResultController.createKeyResult(keyResult,
                Optional.of(company.getUuid()), Optional.empty(), Optional.of(okrSet.getUuid()));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(keyResult);
    }

    // Should return 401
    @Test
    @WithMockUser(username = "testAdmin1", roles = { "NORMAL" }, password = "password")
    public void testCreateKeyResult_Unauthorized() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));
        when(keyResultService.insert(keyResult)).thenReturn(keyResult);

        ResponseEntity<KeyResult> response = keyResultController.createKeyResult(keyResult,
                Optional.of(company.getUuid()), Optional.empty(), Optional.of(okrSet.getUuid()));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", roles = { "BU_ADMIN" }, password = "password", value = "testAdmin1")
    public void testCreateKeyResult_BusinessUnitUnauthorized() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));
        when(keyResultService.insert(keyResult)).thenReturn(keyResult);

        ResponseEntity<KeyResult> response = keyResultController.createKeyResult(keyResult,
                Optional.of(company.getUuid()), Optional.empty(), Optional.of(okrSet.getUuid()));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }

    @Test 
    @WithMockCustomUser(username = "testAdmin1", roles = { "BU_ADMIN" }, password = "password")
    public void testCreateKeyResult_BusinessUnitNotInCompany() {
        // Arrange
        Company company2 = new Company();
        company2.setUuid(UUID.randomUUID());
        when(companyService.findById(company2.getUuid())).thenReturn(Optional.of(company2));
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));
        when(keyResultService.insert(keyResult)).thenReturn(keyResult);

        // Act
        ResponseEntity<KeyResult> response = keyResultController.createKeyResult(keyResult,
                Optional.of(company2.getUuid()), Optional.of(businessUnit.getUuid()), Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", roles = { "BU_ADMIN" }, password = "password")
    public void testCreateKeyResult_UserNotInBusinessUnit() {
        // Arrange
        BusinessUnit businessUnit2 = new BusinessUnit(new HashSet<>(), new HashSet<OKRSet>());
        businessUnit2.setUuid(UUID.randomUUID());
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(businessUnitService.findById(businessUnit2.getUuid())).thenReturn(Optional.of(businessUnit2));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));
        when(keyResultService.insert(keyResult)).thenReturn(keyResult);

        // Act
        ResponseEntity<KeyResult> response = keyResultController.createKeyResult(keyResult,
                Optional.of(company.getUuid()), Optional.of(businessUnit2.getUuid()), Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }


    // Should return 404
    @Test
    @WithMockCustomUser(username = "testAdmin1", roles = { "BU_ADMIN" }, password = "password")
    public void testCreateKeyResult_BusinessUnitNotFound() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.empty());
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));
        when(keyResultService.insert(keyResult)).thenReturn(keyResult);

        ResponseEntity<KeyResult> response = keyResultController.createKeyResult(keyResult,
                Optional.of(company.getUuid()), Optional.of(businessUnit.getUuid()), Optional.of(okrSet.getUuid()));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", roles = { "CO_ADMIN" }, password = "password")
    public void testCreateKeyResult_CompanyNotFound() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.empty());
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));
        when(keyResultService.insert(keyResult)).thenReturn(keyResult);

        ResponseEntity<KeyResult> response = keyResultController.createKeyResult(keyResult,
                Optional.of(company.getUuid()), Optional.empty(), Optional.of(okrSet.getUuid()));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", roles = { "CO_ADMIN" }, password = "password")
    public void testCreateKeyResult_OkrNotFound() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.empty());
        when(keyResultService.insert(keyResult)).thenReturn(keyResult);

        ResponseEntity<KeyResult> response = keyResultController.createKeyResult(keyResult,
                Optional.of(company.getUuid()), Optional.empty(), Optional.of(okrSet.getUuid()));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    // Test for updateKeyResult
    // Should return 200 and the updated key result
    @Test
    @WithMockCustomUser(username = "testAdmin1", roles = { "CO_ADMIN" }, password = "password")
    public void testUpdateKeyResult_Company() {
        // Arrange
        when(keyResultService.findById(keyResult.getUuid())).thenReturn(Optional.of(keyResult));
        when(keyResultService.save(keyResult)).thenReturn(keyResult);
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));

        ResponseEntity<KeyResult> response = keyResultController.updateKeyResult(keyResult.getUuid(), keyResult,
                Optional.of(company.getUuid()), Optional.empty(), Optional.of(okrSet.getUuid()));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(keyResult);
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", roles = { "BU_ADMIN" }, password = "password")
    public void testUpdateKeyResult_BusinessUnit() {
        // Arrange
        when(keyResultService.findById(keyResult.getUuid())).thenReturn(Optional.of(keyResult));
        when(keyResultService.save(keyResult)).thenReturn(keyResult);
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));

        // Act
        ResponseEntity<KeyResult> response = keyResultController.updateKeyResult(keyResult.getUuid(), keyResult,
                Optional.of(company.getUuid()), Optional.of(businessUnit.getUuid()), Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(keyResult);
    }

    // Should return 401
    @Test
    @WithMockCustomUser(username = "testAdmin1", roles = { "NORMAL" }, password = "password")
    public void testUpdateKeyResult_Unauthorized() {
        // Arrange
        when(keyResultService.findById(keyResult.getUuid())).thenReturn(Optional.of(keyResult));
        when(keyResultService.save(keyResult)).thenReturn(keyResult);
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));

        // Act
        ResponseEntity<KeyResult> response = keyResultController.updateKeyResult(keyResult.getUuid(), keyResult,
                Optional.of(company.getUuid()), Optional.empty(), Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", roles = { "BU_ADMIN" }, password = "password")
    public void testUpdateKeyResult_BusinessUnitUnauthorized() {
        // Arrange
        when(keyResultService.findById(keyResult.getUuid())).thenReturn(Optional.of(keyResult));
        when(keyResultService.save(keyResult)).thenReturn(keyResult);
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));

        // Act
        ResponseEntity<KeyResult> response = keyResultController.updateKeyResult(keyResult.getUuid(), keyResult,
                Optional.of(company.getUuid()), Optional.empty(), Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", roles = { "BU_ADMIN" }, password = "password")
    public void testUpdateKeyResult_BusinessUnitNotInCompany() {
        // Arrange
        Company company2 = new Company();
        company2.setUuid(UUID.randomUUID());
        when(keyResultService.findById(keyResult.getUuid())).thenReturn(Optional.of(keyResult));
        when(keyResultService.save(keyResult)).thenReturn(keyResult);
        when(companyService.findById(company2.getUuid())).thenReturn(Optional.of(company2));
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));

        // Act
        ResponseEntity<KeyResult> response = keyResultController.updateKeyResult(keyResult.getUuid(), keyResult,
                Optional.of(company2.getUuid()), Optional.of(businessUnit.getUuid()), Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", roles = { "BU_ADMIN" }, password = "password")
    public void testUpdateKeyResult_UserNotInBusinessUnit() {
        // Arrange
        BusinessUnit businessUnit2 = new BusinessUnit(new HashSet<>(), new HashSet<OKRSet>());
        businessUnit2.setUuid(UUID.randomUUID());
        when(keyResultService.findById(keyResult.getUuid())).thenReturn(Optional.of(keyResult));
        when(keyResultService.save(keyResult)).thenReturn(keyResult);
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(businessUnitService.findById(businessUnit2.getUuid())).thenReturn(Optional.of(businessUnit2));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));

        // Act
        ResponseEntity<KeyResult> response = keyResultController.updateKeyResult(keyResult.getUuid(), keyResult,
                Optional.of(company.getUuid()), Optional.of(businessUnit2.getUuid()), Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }

    // Should return 404
    @Test
    @WithMockCustomUser(username = "testAdmin1", roles = { "CO_ADMIN" }, password = "password")
    public void testUpdateKeyResult_CompanyNotFound() {
        // Arrange
        when(keyResultService.findById(keyResult.getUuid())).thenReturn(Optional.of(keyResult));
        when(keyResultService.save(keyResult)).thenReturn(keyResult);
        when(companyService.findById(company.getUuid())).thenReturn(Optional.empty());
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));

        // Act
        ResponseEntity<KeyResult> response = keyResultController.updateKeyResult(keyResult.getUuid(), keyResult,
                Optional.of(company.getUuid()), Optional.empty(), Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", roles = { "BU_ADMIN" }, password = "password")
    public void testUpdateKeyResult_BusinessUnitNotFound() {
        // Arrange
        when(keyResultService.findById(keyResult.getUuid())).thenReturn(Optional.of(keyResult));
        when(keyResultService.save(keyResult)).thenReturn(keyResult);
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.empty());
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));

        // Act
        ResponseEntity<KeyResult> response = keyResultController.updateKeyResult(keyResult.getUuid(), keyResult,
                Optional.of(company.getUuid()), Optional.of(businessUnit.getUuid()), Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", roles = { "CO_ADMIN" }, password = "password")
    public void testUpdateKeyResult_OkrNotFound() {
        // Arrange
        when(keyResultService.findById(keyResult.getUuid())).thenReturn(Optional.of(keyResult));
        when(keyResultService.save(keyResult)).thenReturn(keyResult);
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<KeyResult> response = keyResultController.updateKeyResult(keyResult.getUuid(), keyResult,
                Optional.of(company.getUuid()), Optional.empty(), Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", roles = { "CO_ADMIN" }, password = "password")
    public void testUpdateKeyResult_KeyResultNotFound() {
        // Arrange
        when(keyResultService.findById(keyResult.getUuid())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<KeyResult> response = keyResultController.updateKeyResult(keyResult.getUuid(), keyResult,
                Optional.of(company.getUuid()), Optional.empty(), Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }


    // Test for deleteKeyResult
    // Should return 200 and the deleted key result
    @Test
    @WithMockCustomUser(username = "testAdmin1", roles = { "CO_ADMIN" }, password = "password")
    public void testDeleteKeyResult_Company() {
        // Arrange
        when(keyResultService.findById(keyResult.getUuid())).thenReturn(Optional.of(keyResult));
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));
        when(keyResultService.deleteByUuid(keyResult.getUuid())).thenReturn(Optional.of(keyResult));

        // Act
        ResponseEntity<KeyResult> response = keyResultController.deleteKeyResult(keyResult.getUuid(),
                Optional.of(company.getUuid()), Optional.empty(), Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(keyResult);
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", roles = { "BU_ADMIN" }, password = "password")
    public void testDeleteKeyResult_BusinessUnit() {
        // Arrange
        when(keyResultService.findById(keyResult.getUuid())).thenReturn(Optional.of(keyResult));
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));
        when(keyResultService.deleteByUuid(keyResult.getUuid())).thenReturn(Optional.of(keyResult));

        // Act
        ResponseEntity<KeyResult> response = keyResultController.deleteKeyResult(keyResult.getUuid(),
                Optional.of(company.getUuid()), Optional.of(businessUnit.getUuid()), Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(keyResult);
    }

    // Should return 401
    @Test
    @WithMockCustomUser(username = "testAdmin1", roles = { "NORMAL" }, password = "password")
    public void testDeleteKeyResult_Unauthorized() {
        // Arrange
        when(keyResultService.findById(keyResult.getUuid())).thenReturn(Optional.of(keyResult));
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));
        when(keyResultService.deleteByUuid(keyResult.getUuid())).thenReturn(Optional.of(keyResult));

        // Act
        ResponseEntity<KeyResult> response = keyResultController.deleteKeyResult(keyResult.getUuid(),
                Optional.of(company.getUuid()), Optional.empty(), Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", roles = { "BU_ADMIN" }, password = "password")
    public void testDeleteKeyResult_BusinessUnitUnauthorized() {
        // Arrange
        when(keyResultService.findById(keyResult.getUuid())).thenReturn(Optional.of(keyResult));
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));
        when(keyResultService.deleteByUuid(keyResult.getUuid())).thenReturn(Optional.of(keyResult));

        // Act
        ResponseEntity<KeyResult> response = keyResultController.deleteKeyResult(keyResult.getUuid(),
                Optional.of(company.getUuid()), Optional.empty(), Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", roles = { "BU_ADMIN" }, password = "password")
    public void testDeleteKeyResult_BusinessUnitNotInCompany() {
        // Arrange
        Company company2 = new Company();
        company2.setUuid(UUID.randomUUID());
        when(keyResultService.findById(keyResult.getUuid())).thenReturn(Optional.of(keyResult));
        when(companyService.findById(company2.getUuid())).thenReturn(Optional.of(company2));
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));
        when(keyResultService.deleteByUuid(keyResult.getUuid())).thenReturn(Optional.of(keyResult));

        // Act
        ResponseEntity<KeyResult> response = keyResultController.deleteKeyResult(keyResult.getUuid(),
                Optional.of(company2.getUuid()), Optional.of(businessUnit.getUuid()), Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", roles = { "BU_ADMIN" }, password = "password")
    public void testDeleteKeyResult_UserNotInBusinessUnit() {
        // Arrange
        BusinessUnit businessUnit2 = new BusinessUnit(new HashSet<>(), new HashSet<OKRSet>());
        businessUnit2.setUuid(UUID.randomUUID());
        when(keyResultService.findById(keyResult.getUuid())).thenReturn(Optional.of(keyResult));
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(businessUnitService.findById(businessUnit2.getUuid())).thenReturn(Optional.of(businessUnit2));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));
        when(keyResultService.deleteByUuid(keyResult.getUuid())).thenReturn(Optional.of(keyResult));

        // Act
        ResponseEntity<KeyResult> response = keyResultController.deleteKeyResult(keyResult.getUuid(),
                Optional.of(company.getUuid()), Optional.of(businessUnit2.getUuid()), Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }

    // Should return 404
    @Test
    @WithMockCustomUser(username = "testAdmin1", roles = { "CO_ADMIN" }, password = "password")
    public void testDeleteKeyResult_CompanyNotFound() {
        // Arrange
        when(keyResultService.findById(keyResult.getUuid())).thenReturn(Optional.of(keyResult));
        when(companyService.findById(company.getUuid())).thenReturn(Optional.empty());
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));
        when(keyResultService.deleteByUuid(keyResult.getUuid())).thenReturn(Optional.of(keyResult));

        // Act
        ResponseEntity<KeyResult> response = keyResultController.deleteKeyResult(keyResult.getUuid(),
                Optional.of(company.getUuid()), Optional.empty(), Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", roles = { "BU_ADMIN" }, password = "password")
    public void testDeleteKeyResult_BusinessUnitNotFound() {
        // Arrange
        when(keyResultService.findById(keyResult.getUuid())).thenReturn(Optional.of(keyResult));
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.empty());
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));
        when(keyResultService.deleteByUuid(keyResult.getUuid())).thenReturn(Optional.of(keyResult));

        // Act
        ResponseEntity<KeyResult> response = keyResultController.deleteKeyResult(keyResult.getUuid(),
                Optional.of(company.getUuid()), Optional.of(businessUnit.getUuid()), Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", roles = { "CO_ADMIN" }, password = "password")
    public void testDeleteKeyResult_OkrNotFound() {
        // Arrange
        when(keyResultService.findById(keyResult.getUuid())).thenReturn(Optional.of(keyResult));
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.empty());
        when(keyResultService.deleteByUuid(keyResult.getUuid())).thenReturn(Optional.of(keyResult));

        // Act
        ResponseEntity<KeyResult> response = keyResultController.deleteKeyResult(keyResult.getUuid(),
                Optional.of(company.getUuid()), Optional.empty(), Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", roles = { "CO_ADMIN" }, password = "password")
    public void testDeleteKeyResult_KeyResultNotFound() {
        // Arrange
        when(keyResultService.findById(keyResult.getUuid())).thenReturn(Optional.empty());
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));
        when(keyResultService.deleteByUuid(keyResult.getUuid())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<KeyResult> response = keyResultController.deleteKeyResult(keyResult.getUuid(),
                Optional.of(company.getUuid()), Optional.empty(), Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }
}