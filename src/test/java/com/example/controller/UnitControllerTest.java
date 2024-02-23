package com.example.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.model.Unit;
import com.example.service.UnitService;

public class UnitControllerTest {

    @Mock
    private UnitService unitService;

    @InjectMocks
    private UnitController unitController;

    private Unit unit;

    private List<Unit> units;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        unit = new Unit();
        unit.setUuid(UUID.randomUUID());
        units = new ArrayList<>();
        units.add(unit);
    }

    // Test for the getAllUnits method
    // Should return 200 OK and a list of units
    @Test
    public void testGetAllUnits() {
        // Arrange
        when(unitService.findAll()).thenReturn(units);

        // Act
        ResponseEntity<List<Unit>> response = unitController.getAllUnits();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(units);
    }

    // Test for the getUnit method
    // Should return 200 OK and a unit
    @Test
    public void testGetUnit() {
        // Arrange
        when(unitService.findById(unit.getUuid())).thenReturn(Optional.ofNullable(unit));

        // Act
        ResponseEntity<Unit> response = unitController.getUnitById(unit.getUuid());

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(unit);
    }

    // should return 404 NOT FOUND
    @Test
    public void testGetUnitNotFound() {
        // Arrange
        when(unitService.findById(unit.getUuid())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Unit> response = unitController.getUnitById(unit.getUuid());

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    // Test for the createUnit method
    // Should return 201 CREATED and a unit
    @Test
    public void testCreateUnit() {
        // Arrange
        when(unitService.insert(unit)).thenReturn(unit);
        when(unitService.findById(unit.getUuid())).thenReturn(Optional.of(unit));

        // Act
        ResponseEntity<Unit> response = unitController.createUnit(unit);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(unit);
    }

    // Should return 500 INTERNAL SERVER ERROR
    @Test
    public void testCreateUnitInternalServerError() {
        // Arrange
        when(unitService.insert(unit)).thenReturn(null);

        // Act
        ResponseEntity<Unit> response = unitController.createUnit(unit);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Should return 500 INTERNAL SERVER ERROR
    @Test
    public void testCreateUnitInternalServerError2() {
        // Arrange
        when(unitService.insert(unit)).thenReturn(unit);
        when(unitService.findById(unit.getUuid())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Unit> response = unitController.createUnit(unit);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Test for updateUnit method
    //Should return 200 OK and a unit
    @Test
    public void testUpdateUnit() {
        // Arrange
        when(unitService.save(unit)).thenReturn(unit);
        when(unitService.findById(unit.getUuid())).thenReturn(Optional.of(unit));

        // Act
        ResponseEntity<Unit> response = unitController.updateUnit(unit.getUuid(), unit);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(unit);
    }

    // Should return 404 NOT FOUND
    @Test
    public void testUpdateUnitNotFound() {
        // Arrange
        when(unitService.save(unit)).thenReturn(null);

        // Act
        ResponseEntity<Unit> response = unitController.updateUnit(unit.getUuid(), unit);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    // Should return 404 NOT FOUND
    @Test
    public void testUpdateUnitNotFound2() {
        // Arrange
        when(unitService.save(unit)).thenReturn(unit);
        when(unitService.findById(unit.getUuid())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Unit> response = unitController.updateUnit(unit.getUuid(), unit);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    //Test for deleteUnit method
    //Should return 200 OK and a unit
    @Test
    public void testDeleteUnit() {
        // Arrange
        when(unitService.findById(unit.getUuid())).thenReturn(Optional.of(unit));
        when(unitService.deleteByUuid(unit.getUuid())).thenReturn(Optional.of(unit));

        // Act
        ResponseEntity<Unit> response = unitController.deleteUnit(unit.getUuid());

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(unit);
    }

    // Should return 404 NOT FOUND
    @Test
    public void testDeleteUnitNotFound() {
        // Arrange
        when(unitService.findById(unit.getUuid())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Unit> response = unitController.deleteUnit(unit.getUuid());

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    // Should return 404 NOT FOUND
    @Test
    public void testDeleteUnitNotFound2() {
        // Arrange
        when(unitService.findById(unit.getUuid())).thenReturn(Optional.of(unit));
        when(unitService.deleteByUuid(unit.getUuid())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Unit> response = unitController.deleteUnit(unit.getUuid());

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }



}
