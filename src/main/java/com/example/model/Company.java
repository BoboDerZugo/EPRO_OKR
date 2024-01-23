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
    private Set<OKRSet> okrSets;
    @DBRef
    private Set<User> employeeSet;

    public Company(OKRSet... okrSets) {
        this.okrSets = Set.of(okrSets);
        this.businessUnits = new HashSet<>();
        this.employeeSet = new HashSet<>();
        this.okrSets = Set.of(okrSets);
    }

    public Set<BusinessUnit> getBusinessUnits() {
        return businessUnits;
    }

    public Set<OKRSet> getOkrSets() {
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

    public void setOkrSets(Set<OKRSet> okrSets){
        this.okrSets = okrSets;
    }

    public void addOKRSet(OKRSet okrSet) {
        // TODO Auto-generated method stub
        this.okrSets.add(okrSet);
    }
}
