package com.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
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

    /**
     * Creates new Company with generated UUID and Sets of BusinessUnits and OKRSets
     *
     * @param businessUnits Set of BusinessUnits
     * @param okrSets       Set of OKRSets
     */
    @PersistenceCreator
    public Company(Set<BusinessUnit> businessUnits, Set<OKRSet> okrSets) {
        this.uuid = UUID.randomUUID();
        this.businessUnits = businessUnits;
        this.okrSets = okrSets;
    }

    /**
     * Creates new Company with generated UUID and OKRSets
     *
     * @param okrSets Array of OKRSets
     */
    public Company(OKRSet... okrSets) {
        this.uuid = UUID.randomUUID();
        this.businessUnits = new HashSet<>();
        this.okrSets = Set.of(okrSets);
    }

    /**
     * UUID Getter
     *
     * @return UUID of BusinessUnit
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * BusinessUnits Getter
     *
     * @return Set of BusinessUnits
     */
    public Set<BusinessUnit> getBusinessUnits() {
        return businessUnits;
    }

    /**
     * OKRSets Getter
     *
     * @return Set of OKRSets
     */
    public Set<OKRSet> getOkrSets() {
        return okrSets;
    }

    /**
     * Add new BusinessUnit into Set of BusinessUnits
     *
     * @param bu New BusinessUnit
     */
    public void addBusinessUnit(BusinessUnit bu) {
        this.businessUnits.add(bu);
    }

    /**
     * OKRSet Setter
     *
     * @param okrSets Set of OKRSets
     */
    public void setOkrSets(Set<OKRSet> okrSets) {
        this.okrSets = okrSets;
    }

    /**
     * Add a new OKRSet into Set of OKRSets
     *
     * @param okrSet Set of OKRSets
     */
    public void addOkrSet(OKRSet okrSet) {
        this.okrSets.add(okrSet);
    }

    /**
     * UUID setter
     *
     * @param fromString New UUID
     */
    public void setUuid(UUID fromString) {
        this.uuid = fromString;
    }

    /**
     * Override of equals() for Company
     *
     * @param obj Compared object
     * @return Equality of Companies
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Company other && this.uuid.equals(other.uuid);
    }
}
