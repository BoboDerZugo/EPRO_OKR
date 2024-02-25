package com.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Set;
import java.util.UUID;

@Document(collection = "Unit")
public class Unit {

    @Id
    private UUID uuid;
    @DBRef
    private Set<User> employeeSet;

    /**
     * Creates a Unit with Generated UUID
     *
     * @param employeeSet Set of Employees for Unit
     */
    @PersistenceCreator
    public Unit(Set<User> employeeSet) {
        this.uuid = UUID.randomUUID();
        Set<User> employeeSet1 = Set.copyOf(employeeSet);
        this.employeeSet = employeeSet1;
    }

    /**
     * Creates a Unit with Generated UUID  using Json
     */
    @JsonCreator
    public Unit() {
        this.uuid = UUID.randomUUID();
        Set<User> employeeSet = Set.of();
        this.employeeSet = employeeSet;
    }

    /**
     * UUID Getter
     *
     * @return UUID of Unit
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * EmployeeSet Getter
     *
     * @return Employees of Unit
     */
    public Set<User> getEmployeeSet() {
        return employeeSet;
    }

    /**
     * Employee adder
     *
     * @param u New Employee for Unit
     */
    public void addEmployee(User u) {
        this.employeeSet.add(u);
    }

    /**
     * UUID Setter
     *
     * @param fromString New UUID for Unit
     */
    public void setUuid(UUID fromString) {
        this.uuid = fromString;
    }

    /**
     * Override of equals() for Unit
     *
     * @param obj Compared Object
     * @return Equality of Units
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Unit other && this.uuid.equals(other.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
