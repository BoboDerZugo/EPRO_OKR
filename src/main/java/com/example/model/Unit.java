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
    // @DBRef
    // private Set<OKRSet> okrSets;

    @PersistenceCreator
    public Unit(Set<User> employeeSet/*, Set<OKRSet> okrSets*/) {
        this.uuid = UUID.randomUUID();
        this.employeeSet = employeeSet;
        //this.okrSets = okrSets;
    }
    
    @JsonCreator
    public Unit() {
        this.uuid = UUID.randomUUID();
    }

    // public Unit(OKRSet[] okr) {
    //     this.uuid = UUID.randomUUID();
    //     this.employeeSet = new HashSet<>();
    //     this.okrSets = Set.of(okr);
    // }

    public UUID getUuid() {
        return uuid;
    }

    public Set<User> getEmployeeSet() {
        return employeeSet;
    }

    // public Set<OKRSet> getOkr() {
    //     return okrSets;
    // }

    public void addEmployee(User u){
        this.employeeSet.add(u);
    }

    public void setUuid(UUID fromString) {
        this.uuid = fromString;
    }

    // public void setOkr(OKRSet... okr) {
    //     this.okrSets = Set.of(okr);
    // }
}
