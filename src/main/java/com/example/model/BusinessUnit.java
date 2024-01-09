package com.example.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class BusinessUnit {
    private UUID uuid;
    private Set<User> employees;
    private Set<Unit> units;
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
        for (int i = 0; i < minLength; i++) {
            this.okrSets[i] = okrSets[i];
        }
    }
}
