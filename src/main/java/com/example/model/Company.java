package com.example.model;

import java.util.HashSet;
import java.util.Set;

public class Company {
    private Set<BusinessUnit> businessUnits;
    private OKRSet[] okrSets;

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
        for (int i = 0; i < minLength; i++) {
            this.okrSets[i] = okrSets[i];
        }
    }
}
