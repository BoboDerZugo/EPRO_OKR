package com.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Document(collection = "BusinessUnit")
public class BusinessUnit {
    @Id
    private UUID uuid;
    @DBRef
    private Set<User> employees;
    @DBRef
    private Set<Unit> units;
    @DBRef
    private OKRSet[] okrSets;

    public BusinessUnit(OKRSet[] okrSets) {
        this.uuid = UUID.randomUUID();
        this.employees = new HashSet<>();
        this.units = new HashSet<>();
        this.okrSets = new OKRSet[5];
        setOkrSets(okrSets);
    }

    public UUID getUuid() {
        return uuid;
    }

    public Set<User> getEmployees() {
        return employees;
    }

    public Set<Unit> getUnits() {
        return units;
    }

    public OKRSet[] getOkrSets() {
        return okrSets;
    }

    public void addEmployee(User u){
        this.employees.add(u);
    }

    public void addUnit(Unit u){
        this.units.add(u);
    }

    public void setOkrSets(OKRSet... okrSets){
        int minLength = Math.min(this.okrSets.length, okrSets.length);
        System.arraycopy(okrSets, 0, this.okrSets, 0, minLength);
    }
}
