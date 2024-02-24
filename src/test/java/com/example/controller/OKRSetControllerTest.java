package com.example.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
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
public class OKRSetControllerTest {

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
    private OKRSetController okrSetController;

    Objective objective;
    KeyResult keyResult;
    OKRSet okrSet1;
    OKRSet okrSet2;
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
        okrSet1 = new OKRSet(objective, keyResult);
        okrSet1.setUuid(UUID.randomUUID());
        okrSet2 = new OKRSet(objective, keyResult);
        okrSet2.setUuid(UUID.randomUUID());
        user = new User("testAdmin1", "password", "BU_ADMIN");
        user.setUuid(UUID.fromString("87559ff1-ae30-4af5-9ba2-1723bed1f706"));
        Set<User> users = new HashSet<>();
        users.add(user);
        unit = new Unit(users);
        unit.setUuid(UUID.randomUUID());
        businessUnit = new BusinessUnit(new HashSet<Unit>(Arrays.asList(unit)),
                new HashSet<OKRSet>(Arrays.asList(okrSet1)));
        businessUnit.setUuid(UUID.randomUUID());
        company = new Company(new HashSet<BusinessUnit>(Arrays.asList(businessUnit)),
                new HashSet<OKRSet>(Arrays.asList(okrSet2)));
        company.setUuid(UUID.randomUUID());
    }

    // Test Get All OKR Sets
    // Should return 200 and a set of OKR Sets
    @Test
    public void testGetAllOKRSets_BusinessUnit() {
        // Arrange
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));

        // Act
        ResponseEntity<Set<OKRSet>> response = okrSetController.getAllOKRSets(Optional.of(company.getUuid()),
                Optional.of(businessUnit.getUuid()));
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(businessUnit.getOkrSets());
    }

    @Test
    void testGetAllOKRSets_Company() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));

        // Act
        ResponseEntity<Set<OKRSet>> response = okrSetController.getAllOKRSets(Optional.of(company.getUuid()),
                Optional.empty());
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(company.getOkrSets());
    }

    // Should return 404
    @Test
    public void testGetAllOKRSets_CompanyNotFound() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Set<OKRSet>> response = okrSetController.getAllOKRSets(Optional.of(company.getUuid()),
                Optional.empty());
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void testGetAllOKRSets_BusinessUnitNotFound() {
        // Arrange
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.empty());
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));

        // Act
        ResponseEntity<Set<OKRSet>> response = okrSetController.getAllOKRSets(Optional.of(company.getUuid()),
                Optional.of(businessUnit.getUuid()));
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    // Test Get OKR Set By ID
    // Should return 200 and an OKR Set
    @Test
    public void testGetOKRSetById() {
        // Arrange
        when(okrSetService.findById(okrSet1.getUuid())).thenReturn(Optional.of(okrSet1));

        // Act
        ResponseEntity<OKRSet> response = okrSetController.getOKRSetById(okrSet1.getUuid());
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(okrSet1);
    }

    // Should return 404
    @Test
    public void testGetOKRSetById_NotFound() {
        // Arrange
        when(okrSetService.findById(okrSet1.getUuid())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<OKRSet> response = okrSetController.getOKRSetById(okrSet1.getUuid());
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    // Test Create OKR Set
    // Should return 201 and an OKR Set
    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "BU_ADMIN" })
    public void testCreateOKRSet_BusinessUnit() {
        // Arrange
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.save(okrSet1)).thenReturn(okrSet1);

        // Act
        ResponseEntity<OKRSet> response = okrSetController.createOKRSet(okrSet1, Optional.of(company.getUuid()),
                Optional.of(businessUnit.getUuid()));
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(okrSet1);
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "CO_ADMIN" })
    public void testCreateOKRSet_Company() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.save(okrSet1)).thenReturn(okrSet1);

        // Act
        ResponseEntity<OKRSet> response = okrSetController.createOKRSet(okrSet1, Optional.of(company.getUuid()),
                Optional.empty());
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(okrSet1);
    }

    // Should return 401
    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "NORMAL" })
    public void testCreateOKRSet_BusinessUnitUnauthorized() {
        // Arrange
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.save(okrSet1)).thenReturn(okrSet1);

        // Act
        ResponseEntity<OKRSet> response = okrSetController.createOKRSet(okrSet1, Optional.of(company.getUuid()),
                Optional.of(businessUnit.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "BU_ADMIN" })
    public void testCreateOKRSet_CompanyUnauthorized() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.save(okrSet1)).thenReturn(okrSet1);

        // Act
        ResponseEntity<OKRSet> response = okrSetController.createOKRSet(okrSet1, Optional.of(company.getUuid()),
                Optional.empty());

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "BU_ADMIN" })
    public void testCreateOKRSet_BusinessUnitNotPartOfCompany() {
        // Arrange
        Company company2 = new Company();
        company2.setUuid(UUID.randomUUID());
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(companyService.findById(company2.getUuid())).thenReturn(Optional.of(company2));
        when(okrSetService.save(okrSet1)).thenReturn(okrSet1);

        // Act
        ResponseEntity<OKRSet> response = okrSetController.createOKRSet(okrSet1, Optional.of(company.getUuid()),
                Optional.of(businessUnit.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "BU_ADMIN" })
    public void testCreateOKRSet_OKRSetNotPartOfBusinessUnit() {
        // Arrange
        OKRSet okrSet3 = new OKRSet(objective, keyResult);
        okrSet3.setUuid(UUID.randomUUID());
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.save(okrSet3)).thenReturn(okrSet3);

        // Act
        ResponseEntity<OKRSet> response = okrSetController.createOKRSet(okrSet3, Optional.of(company.getUuid()),
                Optional.of(businessUnit.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }

    //Should return 404
    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "BU_ADMIN" })
    public void testCreateOKRSet_BusinessUnitNotFound() {
        // Arrange
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.empty());
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.save(okrSet1)).thenReturn(okrSet1);

        // Act
        ResponseEntity<OKRSet> response = okrSetController.createOKRSet(okrSet1, Optional.of(company.getUuid()),
                Optional.of(businessUnit.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "CO_ADMIN" })
    public void testCreateOKRSet_CompanyNotFound() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.empty());
        when(okrSetService.save(okrSet1)).thenReturn(okrSet1);

        // Act
        ResponseEntity<OKRSet> response = okrSetController.createOKRSet(okrSet1, Optional.of(company.getUuid()),
                Optional.empty());

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    // Test Update OKR Set
    // Should return 200 and an OKR Set
    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "BU_ADMIN" })
    public void testUpdateOKRSet_BusinessUnit() {
        // Arrange
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.findById(okrSet1.getUuid())).thenReturn(Optional.of(okrSet1));
        when(okrSetService.save(okrSet1)).thenReturn(okrSet1);
        
        // Act
        ResponseEntity<OKRSet> response = okrSetController.updateOKRSet(okrSet1.getUuid(), okrSet1, Optional.of(company.getUuid()),
                Optional.of(businessUnit.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(okrSet1);
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "CO_ADMIN" })
    public void testUpdateOKRSet_Company() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.findById(okrSet1.getUuid())).thenReturn(Optional.of(okrSet1));
        when(okrSetService.save(okrSet1)).thenReturn(okrSet1);
        
        // Act
        ResponseEntity<OKRSet> response = okrSetController.updateOKRSet(okrSet1.getUuid(), okrSet1, Optional.of(company.getUuid()),
                Optional.empty());

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(okrSet1);
    }

    // Should return 401
    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "NORMAL" })
    public void testUpdateOKRSet_BusinessUnitUnauthorized() {
        // Arrange
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.findById(okrSet1.getUuid())).thenReturn(Optional.of(okrSet1));
        when(okrSetService.save(okrSet1)).thenReturn(okrSet1);
        
        // Act
        ResponseEntity<OKRSet> response = okrSetController.updateOKRSet(okrSet1.getUuid(), okrSet1, Optional.of(company.getUuid()),
                Optional.of(businessUnit.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "BU_ADMIN" })
    public void testUpdateOKRSet_CompanyUnauthorized() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.findById(okrSet1.getUuid())).thenReturn(Optional.of(okrSet1));
        when(okrSetService.save(okrSet1)).thenReturn(okrSet1);
        
        // Act
        ResponseEntity<OKRSet> response = okrSetController.updateOKRSet(okrSet1.getUuid(), okrSet1, Optional.of(company.getUuid()),
                Optional.empty());

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "BU_ADMIN" })
    public void testUpdateOKRSet_BusinessUnitNotPartOfCompany() {
        // Arrange
        Company company2 = new Company();
        company2.setUuid(UUID.randomUUID());
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(companyService.findById(company2.getUuid())).thenReturn(Optional.of(company2));
        when(okrSetService.findById(okrSet1.getUuid())).thenReturn(Optional.of(okrSet1));
        when(okrSetService.save(okrSet1)).thenReturn(okrSet1);
        
        // Act
        ResponseEntity<OKRSet> response = okrSetController.updateOKRSet(okrSet1.getUuid(), okrSet1, Optional.of(company2.getUuid()),
                Optional.of(businessUnit.getUuid()));

        // Assert

        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "BU_ADMIN" })
    public void testUpdateOKRSet_OKRSetNotPartOfBusinessUnit() {
        // Arrange
        OKRSet okrSet3 = new OKRSet(objective, keyResult);
        okrSet3.setUuid(UUID.randomUUID());
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.findById(okrSet3.getUuid())).thenReturn(Optional.of(okrSet3));
        when(okrSetService.save(okrSet3)).thenReturn(okrSet3);
        
        // Act
        ResponseEntity<OKRSet> response = okrSetController.updateOKRSet(okrSet3.getUuid(), okrSet3, Optional.of(company.getUuid()),
                Optional.of(businessUnit.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }

    //Should return 404 
    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "BU_ADMIN" })
    public void testUpdateOKRSet_BusinessUnitNotFound() {
        // Arrange
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.empty());
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.findById(okrSet1.getUuid())).thenReturn(Optional.of(okrSet1));
        when(okrSetService.save(okrSet1)).thenReturn(okrSet1);

        // Act
        ResponseEntity<OKRSet> response = okrSetController.updateOKRSet(okrSet1.getUuid(), okrSet1, Optional.of(company.getUuid()),
                Optional.of(businessUnit.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "CO_ADMIN" })
    public void testUpdateOKRSet_CompanyNotFound() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.empty());
        when(okrSetService.findById(okrSet1.getUuid())).thenReturn(Optional.of(okrSet1));
        when(okrSetService.save(okrSet1)).thenReturn(okrSet1);

        // Act
        ResponseEntity<OKRSet> response = okrSetController.updateOKRSet(okrSet1.getUuid(), okrSet1, Optional.of(company.getUuid()),
                Optional.empty());

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "BU_ADMIN" })
    public void testUpdateOKRSet_OKRSetNotFound() {
        // Arrange
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.findById(okrSet1.getUuid())).thenReturn(Optional.empty());
        when(okrSetService.save(okrSet1)).thenReturn(okrSet1);

        // Act
        ResponseEntity<OKRSet> response = okrSetController.updateOKRSet(okrSet1.getUuid(), okrSet1, Optional.of(company.getUuid()),
                Optional.of(businessUnit.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    // Test Delete OKR Set
    // Should return 200 and an OKR Set
    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "BU_ADMIN" })
    public void testDeleteOKRSet_BusinessUnit() {
        // Arrange
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.findById(okrSet1.getUuid())).thenReturn(Optional.of(okrSet1));
        when(okrSetService.save(okrSet1)).thenReturn(okrSet1);

        // Act
        ResponseEntity<OKRSet> response = okrSetController.deleteOKRSet(okrSet1.getUuid(), Optional.of(company.getUuid()),
                Optional.of(businessUnit.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(okrSet1);
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "CO_ADMIN" })
    public void testDeleteOKRSet_Company() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.findById(okrSet2.getUuid())).thenReturn(Optional.of(okrSet2));
        when(okrSetService.save(okrSet2)).thenReturn(okrSet2);

        // Act
        ResponseEntity<OKRSet> response = okrSetController.deleteOKRSet(okrSet2.getUuid(), Optional.of(company.getUuid()),
                Optional.empty());

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(okrSet2);
    }

    // Should return 401
    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "NORMAL" })
    public void testDeleteOKRSet_BusinessUnitUnauthorized() {
        // Arrange
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.findById(okrSet1.getUuid())).thenReturn(Optional.of(okrSet1));
        when(okrSetService.save(okrSet1)).thenReturn(okrSet1);

        // Act
        ResponseEntity<OKRSet> response = okrSetController.deleteOKRSet(okrSet1.getUuid(), Optional.of(company.getUuid()),
                Optional.of(businessUnit.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "BU_ADMIN" })
    public void testDeleteOKRSet_CompanyUnauthorized() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.findById(okrSet2.getUuid())).thenReturn(Optional.of(okrSet2));
        when(okrSetService.save(okrSet2)).thenReturn(okrSet2);

        // Act
        ResponseEntity<OKRSet> response = okrSetController.deleteOKRSet(okrSet2.getUuid(), Optional.of(company.getUuid()),
                Optional.empty());

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "BU_ADMIN" })
    public void testDeleteOKRSet_BusinessUnitNotPartOfCompany() {
        // Arrange
        Company company2 = new Company();
        company2.setUuid(UUID.randomUUID());
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(companyService.findById(company2.getUuid())).thenReturn(Optional.of(company2));
        when(okrSetService.findById(okrSet1.getUuid())).thenReturn(Optional.of(okrSet1));
        when(okrSetService.save(okrSet1)).thenReturn(okrSet1);

        // Act
        ResponseEntity<OKRSet> response = okrSetController.deleteOKRSet(okrSet1.getUuid(), Optional.of(company2.getUuid()),
                Optional.of(businessUnit.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();

    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "BU_ADMIN" })
    public void testDeleteOKRSet_OKRSetNotPartOfBusinessUnit() {
        // Arrange
        OKRSet okrSet3 = new OKRSet(objective, keyResult);
        okrSet3.setUuid(UUID.randomUUID());
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.findById(okrSet3.getUuid())).thenReturn(Optional.of(okrSet3));
        when(okrSetService.save(okrSet3)).thenReturn(okrSet3);

        // Act
        ResponseEntity<OKRSet> response = okrSetController.deleteOKRSet(okrSet3.getUuid(), Optional.of(company.getUuid()),
                Optional.of(businessUnit.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }

    //Should return 404
    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "BU_ADMIN" })
    public void testDeleteOKRSet_BusinessUnitNotFound() {
        // Arrange
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.empty());
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.findById(okrSet1.getUuid())).thenReturn(Optional.of(okrSet1));
        when(okrSetService.save(okrSet1)).thenReturn(okrSet1);

        // Act
        ResponseEntity<OKRSet> response = okrSetController.deleteOKRSet(okrSet1.getUuid(), Optional.of(company.getUuid()),
                Optional.of(businessUnit.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "CO_ADMIN" })
    public void testDeleteOKRSet_CompanyNotFound() {
        // Arrange
        when(companyService.findById(company.getUuid())).thenReturn(Optional.empty());
        when(okrSetService.findById(okrSet2.getUuid())).thenReturn(Optional.of(okrSet2));
        when(okrSetService.save(okrSet2)).thenReturn(okrSet2);

        // Act
        ResponseEntity<OKRSet> response = okrSetController.deleteOKRSet(okrSet2.getUuid(), Optional.of(company.getUuid()),
                Optional.empty());

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @WithMockCustomUser(username = "testAdmin1", password = "password", roles = { "BU_ADMIN" })
    public void testDeleteOKRSet_OKRSetNotFound() {
        // Arrange
        when(businessUnitService.findById(businessUnit.getUuid())).thenReturn(Optional.of(businessUnit));
        when(companyService.findById(company.getUuid())).thenReturn(Optional.of(company));
        when(okrSetService.findById(okrSet1.getUuid())).thenReturn(Optional.empty());
        when(okrSetService.save(okrSet1)).thenReturn(okrSet1);

        // Act
        ResponseEntity<OKRSet> response = okrSetController.deleteOKRSet(okrSet1.getUuid(), Optional.of(company.getUuid()),
                Optional.of(businessUnit.getUuid()));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }


}
