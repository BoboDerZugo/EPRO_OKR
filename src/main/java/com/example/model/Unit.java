package com.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Document(collection = "Unit")
public class Unit {
    @Id
    private UUID uuid;
    @DBRef
    private Set<User> employeeSet;
    @DBRef
    private OKRSet[] okrSets;

    public Unit(OKRSet[] okr) {
        this.uuid = UUID.randomUUID();
        this.employeeSet = new HashSet<>();
        this.okrSets = okr;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Set<User> getEmployeeSet() {
        return employeeSet;
    }

    public OKRSet[] getOkr() {
        return okrSets;
    }

    public void addEmployee(User u){
        this.employeeSet.add(u);
    }

    public void setOkr(OKRSet... okr) {
        this.okrSets = okr;
    }
}
