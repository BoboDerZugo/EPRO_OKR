package com.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Document(collection = "Company")
public class Company {
    @Id
    private UUID uuid;
    @DBRef
    private Set<BusinessUnit> businessUnits;
    @DBRef
    private OKRSet[] okrSets;
    @DBRef
    private Set<User> employeeSet;

    public Company(OKRSet... okrSets) {
        this.okrSets = new OKRSet[5];
        this.businessUnits = new HashSet<>();
        this.employeeSet = new HashSet<>();
        setOkrSets(okrSets);
    }

    public Set<BusinessUnit> getBusinessUnits() {
        return businessUnits;
    }

    public OKRSet[] getOkrSets() {
        return okrSets;
    }

    public Set<User> getEmployeeSet() {
        return employeeSet;
    }

    public void addBusinessUnit(BusinessUnit bu){
        this.businessUnits.add(bu);
    }

    public void addEmployee(User u){
        this.employeeSet.add(u);
    }

    public void setOkrSets(OKRSet... okrSets){
        int minLength = Math.min(this.okrSets.length, okrSets.length);
        System.arraycopy(okrSets, 0, this.okrSets, 0, minLength);
    }
}
