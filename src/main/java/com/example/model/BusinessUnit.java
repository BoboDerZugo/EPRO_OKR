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
    private Set<User> employeeSet;
    @DBRef
    private Set<Unit> units;
    @DBRef
    private OKRSet[] okrSets;

    public BusinessUnit(OKRSet[] okrSets) {
        this.uuid = UUID.randomUUID();
        this.employeeSet = new HashSet<>();
        this.units = new HashSet<>();
        this.okrSets = new OKRSet[5];
        setOkrSets(okrSets);
    }

    public UUID getUuid() {
        return uuid;
    }

    public Set<User> getEmployeeSet() {
        return employeeSet;
    }

    public Set<Unit> getUnits() {
        return units;
    }

    public OKRSet[] getOkrSets() {
        return okrSets;
    }

    public void addEmployee(User u){
        this.employeeSet.add(u);
    }

    public void addUnit(Unit u){
        this.units.add(u);
    }

    public void setOkrSets(OKRSet... okrSets){
        int minLength = Math.min(this.okrSets.length, okrSets.length);
        System.arraycopy(okrSets, 0, this.okrSets, 0, minLength);
    }
}
