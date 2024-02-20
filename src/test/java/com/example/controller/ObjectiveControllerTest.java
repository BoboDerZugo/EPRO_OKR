package com.example.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
import com.example.service.ObjectiveService;

@SpringBootTest
public class ObjectiveControllerTest {
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

    @Mock
    private ObjectiveService objectiveService;

    @InjectMocks
    private ObjectiveController objectiveController;

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

    // Test getObjective()
    // Should return 200 OK and the objective
    @Test
    public void testGetObjective_BusinesUnitOkrFound() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));
        when(objectiveService.findById(objective.getUuid())).thenReturn(Optional.of(objective));

        // Act

        ResponseEntity<Objective> response = objectiveController.getObjective(Optional.of(company.getUuid()),
                Optional.of(businessUnit.getUuid()),
                Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(objective);
    }

    @Test
    public void testGetObjective_CompanyOkrFound() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));
        when(objectiveService.findById(objective.getUuid())).thenReturn(Optional.of(objective));

        // Act

        ResponseEntity<Objective> response = objectiveController.getObjective(Optional.of(company.getUuid()),
                Optional.empty(),
                Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(objective);
    }

    // should return 404 NOT FOUND
    @Test
    public void testGetObjective_BusinesUnitOkrNotFound() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.empty());
        when(objectiveService.findById(objective.getUuid())).thenReturn(Optional.empty());

        // Act

        ResponseEntity<Objective> response = objectiveController.getObjective(Optional.of(company.getUuid()),
                Optional.of(businessUnit.getUuid()),
                Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void testGetObjective_CompanyOkrNotFound() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.empty());
        when(objectiveService.findById(objective.getUuid())).thenReturn(Optional.empty());

        // Act

        ResponseEntity<Objective> response = objectiveController.getObjective(Optional.of(company.getUuid()),
                Optional.empty(),
                Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    // Test getObjectiveById()
    // Should return 200 OK and the objective
    @Test
    public void testGetObjectiveById() {
        // Arrange
        when(objectiveService.findById(objective.getUuid())).thenReturn(Optional.of(objective));

        // Act

        ResponseEntity<Objective> response = objectiveController.getObjectiveById(objective.getUuid());

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(objective);
    }

    // Should return 404 NOT FOUND
    @Test
    public void testGetObjectiveById_NotFound() {
        // Arrange
        when(objectiveService.findById(objective.getUuid())).thenReturn(Optional.empty());

        // Act

        ResponseEntity<Objective> response = objectiveController.getObjectiveById(objective.getUuid());

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    // Test createObjective()
    // Should return 201 CREATED and the objective
    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "BU_ADMIN" })
    public void testCreateObjective_BusinessUnit() {
        // Arrange
        OKRSet okrSet2 = okrSet;
        okrSet2.setObjective(null);
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet2));
        when(objectiveService.save(objective)).thenReturn(objective);

        // Act
        ResponseEntity<Objective> response = objectiveController.createObjective(objective,
                Optional.of(company.getUuid()), Optional.of(businessUnit.getUuid()),
                Optional.of(okrSet2.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(objective);
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "CO_ADMIN" })
    public void testCreateObjective_Company() {
        // Arrange
        OKRSet okrSet2 = okrSet;
        okrSet2.setObjective(null);
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet2));
        when(objectiveService.save(objective)).thenReturn(objective);
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));

        // Act
        ResponseEntity<Objective> response = objectiveController.createObjective(objective,
                Optional.of(company.getUuid()), Optional.empty(),
                Optional.of(okrSet2.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(objective);
    }

    // Should return 401 UNAUTHORIZED
    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "NORMAL" })
    public void testCreateObjective_BusinessUnitUnauthorized() {
        // Arrange
        OKRSet okrSet2 = okrSet;
        okrSet2.setObjective(null);
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet2));
        when(objectiveService.save(objective)).thenReturn(objective);

        // Act
        ResponseEntity<Objective> response = objectiveController.createObjective(objective,
                Optional.of(company.getUuid()), Optional.of(businessUnit.getUuid()),
                Optional.of(okrSet2.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "BU_ADMIN" })
    public void testCreateObjective_CompanyUnauthorized() {
        // Arrange
        OKRSet okrSet2 = okrSet;
        okrSet2.setObjective(null);
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet2));
        when(objectiveService.save(objective)).thenReturn(objective);
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));

        // Act
        ResponseEntity<Objective> response = objectiveController.createObjective(objective,
                Optional.of(company.getUuid()), Optional.empty(),
                Optional.of(okrSet2.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "BU_ADMIN" })
    public void testCreateObjective_UserNotInBusinessUnit() {
        // Arrange
        OKRSet okrSet2 = okrSet;
        okrSet2.setObjective(null);
        BusinessUnit businessUnit2 = new BusinessUnit(new HashSet<Unit>(), new HashSet<OKRSet>());
        businessUnit2.setUuid(UUID.randomUUID());
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(businessUnitService.findById(businessUnit2.getUuid())).thenReturn(Optional.of(businessUnit2));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet2));
        when(objectiveService.save(objective)).thenReturn(objective);

        // Act
        ResponseEntity<Objective> response = objectiveController.createObjective(objective,
                Optional.of(company.getUuid()), Optional.of(businessUnit2.getUuid()),
                Optional.of(okrSet2.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "BU_ADMIN" })
    public void testCreateObjective_BusinessUnitNotInCompany() {
        // Arrange
        OKRSet okrSet2 = okrSet;
        okrSet2.setObjective(null);
        Company company2 = new Company(new HashSet<BusinessUnit>(), new HashSet<OKRSet>());
        company2.setUuid(UUID.randomUUID());
        when(companyService.findById(company2.getUuid())).thenReturn(Optional.of(company2));
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet2));
        when(objectiveService.save(objective)).thenReturn(objective);

        // Act
        ResponseEntity<Objective> response = objectiveController.createObjective(objective,
                Optional.of(company2.getUuid()), Optional.of(businessUnit.getUuid()),
                Optional.of(okrSet2.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }

    // should return 404 NOT FOUND
    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "BU_ADMIN" })
    public void testCreateObjective_OkrNotFound() {
        // Arrange
        OKRSet okrSet2 = okrSet;
        okrSet2.setObjective(null);
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.empty());
        when(objectiveService.save(objective)).thenReturn(objective);

        // Act
        ResponseEntity<Objective> response = objectiveController.createObjective(objective,
                Optional.of(company.getUuid()), Optional.of(businessUnit.getUuid()),
                Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "CO_ADMIN" })
    public void testCreateObjective_CompanyNotFound() {
        // Arrange
        OKRSet okrSet2 = okrSet;
        okrSet2.setObjective(null);
        when(companyService.findById(company.getUuid())).thenReturn(Optional.empty());
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet2));
        when(objectiveService.save(objective)).thenReturn(objective);

        // Act
        ResponseEntity<Objective> response = objectiveController.createObjective(objective,
                Optional.of(company.getUuid()), Optional.empty(),
                Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    // should return 409 CONFLICT
    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "CO_ADMIN" })
    public void testCreateObjective_OkrHasObjective() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));
        when(objectiveService.save(objective)).thenReturn(objective);
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));

        // Act
        ResponseEntity<Objective> response = objectiveController.createObjective(objective,
                Optional.of(company.getUuid()), Optional.empty(),
                Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody()).isNull();
    }

    // Test updateObjective()
    // Should return 200 OK and the updated objective
    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "BU_ADMIN" })
    public void testUpdateObjective_BusinessUnit() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));
        when(objectiveService.findById(objective.getUuid())).thenReturn(Optional.of(objective));
        when(objectiveService.save(objective)).thenReturn(objective);

        // Act
        ResponseEntity<Objective> response = objectiveController.updateObjective(objective,
                Optional.of(company.getUuid()), Optional.of(businessUnit.getUuid()),
                Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(objective);
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "CO_ADMIN" })
    public void testUpdateObjective_Company() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));
        when(objectiveService.findById(objective.getUuid())).thenReturn(Optional.of(objective));
        when(objectiveService.save(objective)).thenReturn(objective);
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));

        // Act
        ResponseEntity<Objective> response = objectiveController.updateObjective(objective,
                Optional.of(company.getUuid()), Optional.empty(),
                Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(objective);
    }

    // Should return 401 UNAUTHORIZED#
    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "NORMAL" })
    public void testUpdateObjective_BusinessUnitUnauthorized() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));
        when(objectiveService.findById(objective.getUuid())).thenReturn(Optional.of(objective));
        when(objectiveService.save(objective)).thenReturn(objective);

        // Act
        ResponseEntity<Objective> response = objectiveController.updateObjective(objective,
                Optional.of(company.getUuid()), Optional.of(businessUnit.getUuid()),
                Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "BU_ADMIN" })
    public void testUpdateObjective_CompanyUnauthorized() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));
        when(objectiveService.findById(objective.getUuid())).thenReturn(Optional.of(objective));
        when(objectiveService.save(objective)).thenReturn(objective);
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));

        // Act
        ResponseEntity<Objective> response = objectiveController.updateObjective(objective,
                Optional.of(company.getUuid()), Optional.empty(),
                Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "BU_ADMIN" })
    public void testUpdateObjective_UserNotInBusinessUnit() {
        // Arrange
        BusinessUnit businessUnit2 = new BusinessUnit(new HashSet<Unit>(), new HashSet<OKRSet>());
        businessUnit2.setUuid(UUID.randomUUID());
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(businessUnitService.findById(businessUnit2.getUuid())).thenReturn(Optional.of(businessUnit2));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));
        when(objectiveService.findById(objective.getUuid())).thenReturn(Optional.of(objective));
        when(objectiveService.save(objective)).thenReturn(objective);

        // Act
        ResponseEntity<Objective> response = objectiveController.updateObjective(objective,
                Optional.of(company.getUuid()), Optional.of(businessUnit2.getUuid()),
                Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "BU_ADMIN" })
    public void testUpdateObjective_BusinessUnitNotInCompany() {
        // Arrange
        Company company2 = new Company(new HashSet<BusinessUnit>(), new HashSet<OKRSet>());
        company2.setUuid(UUID.randomUUID());
        when(companyService.findById(company2.getUuid())).thenReturn(Optional.of(company2));
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));
        when(objectiveService.findById(objective.getUuid())).thenReturn(Optional.of(objective));
        when(objectiveService.save(objective)).thenReturn(objective);

        // Act
        ResponseEntity<Objective> response = objectiveController.updateObjective(objective,
                Optional.of(company2.getUuid()), Optional.of(businessUnit.getUuid()),
                Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }

    // should return 404 NOT FOUND
    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "BU_ADMIN" })
    public void testUpdateObjective_OkrNotFound() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.empty());
        when(objectiveService.findById(objective.getUuid())).thenReturn(Optional.of(objective));
        when(objectiveService.save(objective)).thenReturn(objective);

        // Act
        ResponseEntity<Objective> response = objectiveController.updateObjective(objective,
                Optional.of(company.getUuid()), Optional.of(businessUnit.getUuid()),
                Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "CO_ADMIN" })
    public void testUpdateObjective_CompanyNotFound() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.empty());
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));
        when(objectiveService.findById(objective.getUuid())).thenReturn(Optional.of(objective));
        when(objectiveService.save(objective)).thenReturn(objective);

        // Act
        ResponseEntity<Objective> response = objectiveController.updateObjective(objective,
                Optional.of(company.getUuid()), Optional.empty(),
                Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "CO_ADMIN" })
    public void testUpdateObjective_BusinessUnitNotFound() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.empty());
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));
        when(objectiveService.findById(objective.getUuid())).thenReturn(Optional.of(objective));
        when(objectiveService.save(objective)).thenReturn(objective);

        // Act
        ResponseEntity<Objective> response = objectiveController.updateObjective(objective,
                Optional.of(company.getUuid()), Optional.of(businessUnit.getUuid()),
                Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

   //Test deleteObjective()
    //Should return 200 OK
    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "BU_ADMIN" })
    public void testDeleteObjective_BusinessUnit() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));
        when(objectiveService.findById(objective.getUuid())).thenReturn(Optional.of(objective));

        // Act
        ResponseEntity<Objective> response = objectiveController.deleteObjective(Optional.of(company.getUuid()),
                Optional.of(businessUnit.getUuid()), Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(objective);
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "CO_ADMIN" })
    public void testDeleteObjective_Company() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));
        when(objectiveService.findById(objective.getUuid())).thenReturn(Optional.of(objective));
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));

        // Act
        ResponseEntity<Objective> response = objectiveController.deleteObjective(Optional.of(company.getUuid()),
                Optional.empty(), Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(objective);
    }

    //Should return 401 UNAUTHORIZED
    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "NORMAL" })
    public void testDeleteObjective_BusinessUnitUnauthorized() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));
        when(objectiveService.findById(objective.getUuid())).thenReturn(Optional.of(objective));

        // Act
        ResponseEntity<Objective> response = objectiveController.deleteObjective(Optional.of(company.getUuid()),
                Optional.of(businessUnit.getUuid()), Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "BU_ADMIN" })
    public void testDeleteObjective_CompanyUnauthorized() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));
        when(objectiveService.findById(objective.getUuid())).thenReturn(Optional.of(objective));
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));

        // Act
        ResponseEntity<Objective> response = objectiveController.deleteObjective(Optional.of(company.getUuid()),
                Optional.empty(), Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "BU_ADMIN" })
    public void testDeleteObjective_UserNotInBusinessUnit() {
        // Arrange
        BusinessUnit businessUnit2 = new BusinessUnit(new HashSet<Unit>(), new HashSet<OKRSet>());
        businessUnit2.setUuid(UUID.randomUUID());
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(businessUnitService.findById(businessUnit2.getUuid())).thenReturn(Optional.of(businessUnit2));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));
        when(objectiveService.findById(objective.getUuid())).thenReturn(Optional.of(objective));

        // Act
        ResponseEntity<Objective> response = objectiveController.deleteObjective(Optional.of(company.getUuid()),
                Optional.of(businessUnit2.getUuid()), Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "BU_ADMIN" })
    public void testDeleteObjective_BusinessUnitNotInCompany() {
        // Arrange
        Company company2 = new Company(new HashSet<BusinessUnit>(), new HashSet<OKRSet>());
        company2.setUuid(UUID.randomUUID());
        when(companyService.findById(company2.getUuid())).thenReturn(Optional.of(company2));
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));
        when(objectiveService.findById(objective.getUuid())).thenReturn(Optional.of(objective));

        // Act
        ResponseEntity<Objective> response = objectiveController.deleteObjective(Optional.of(company2.getUuid()),
                Optional.of(businessUnit.getUuid()), Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }

    //should return 404 NOT FOUND
    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "BU_ADMIN" })
    public void testDeleteObjective_OkrNotFound() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.empty());
        when(objectiveService.findById(objective.getUuid())).thenReturn(Optional.of(objective));

        // Act
        ResponseEntity<Objective> response = objectiveController.deleteObjective(Optional.of(company.getUuid()),
                Optional.of(businessUnit.getUuid()), Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "CO_ADMIN" })
    public void testDeleteObjective_CompanyNotFound() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.empty());
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));
        when(objectiveService.findById(objective.getUuid())).thenReturn(Optional.of(objective));

        // Act
        ResponseEntity<Objective> response = objectiveController.deleteObjective(Optional.of(company.getUuid()),
                Optional.of(businessUnit.getUuid()), Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "CO_ADMIN" })
    public void testDeleteObjective_BusinessUnitNotFound() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.empty());
        when(okrSetService.findById(okrSet.getUuid())).thenReturn(Optional.of(okrSet));
        when(objectiveService.findById(objective.getUuid())).thenReturn(Optional.of(objective));

        // Act
        ResponseEntity<Objective> response = objectiveController.deleteObjective(Optional.of(company.getUuid()),
                Optional.of(businessUnit.getUuid()), Optional.of(okrSet.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

}
