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
    private Set<User> employees;
    @DBRef
    private OKRSet[] okr;

    public Unit(OKRSet[] okr) {
        this.uuid = UUID.randomUUID();
        this.employees = new HashSet<>();
        this.okr = okr;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Set<User> getEmployees() {
        return employees;
    }

    public OKRSet[] getOkr() {
        return okr;
    }

    public void addEmployee(User u){
        this.employees.add(u);
    }

    public void setOkr(OKRSet... okr) {
        this.okr = okr;
    }
}
