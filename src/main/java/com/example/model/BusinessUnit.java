package com.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Document(collection = "BusinessUnit")
public class BusinessUnit {
    @Id
    private UUID uuid;
    // @DBRef
    // private Set<User> employeeSet;
    @DBRef
    private Set<Unit> units;
    @DBRef
    private Set<OKRSet> okrSets;

    @PersistenceCreator
    public BusinessUnit(/*Set<User> employeeSet,*/ Set<Unit> units, Set<OKRSet> okrSets) {
        this.uuid = UUID.randomUUID();
        //this.employeeSet = employeeSet;
        this.units = units;
        this.okrSets = okrSets;
    }

    public BusinessUnit(OKRSet[] okrSets) {
        this.uuid = UUID.randomUUID();
        // this.employeeSet = new HashSet<>();
        this.units = new HashSet<>();
        this.okrSets = new HashSet<>();
        this.okrSets = Set.of(okrSets);
    }

    public UUID getUuid() {
        return uuid;
    }

    // public Set<User> getEmployeeSet() {
    //     return employeeSet;
    // }

    public Set<Unit> getUnits() {
        return units;
    }

    public Set<OKRSet> getOkrSets() {
        return okrSets;
    }

    // public void addEmployee(User u){
    //     this.employeeSet.add(u);
    // }

    public void addUnit(Unit u){
        this.units.add(u);
    }

    public void setOkrSets(Set<OKRSet> okrSets){
        this.okrSets = okrSets;
    }

    public void addOkrSet(OKRSet okrSet) {
        this.okrSets.add(okrSet);
    }

    public void setUuid(UUID fromString) {
        this.uuid = fromString;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BusinessUnit) {
            BusinessUnit other = (BusinessUnit) obj;
            if(this.uuid.equals(other.uuid)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

}
