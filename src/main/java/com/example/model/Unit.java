package com.example.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Unit {
    private UUID uuid;
    private Set<User> employees;
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
