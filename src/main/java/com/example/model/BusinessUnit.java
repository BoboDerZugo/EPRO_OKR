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
    @DBRef
    private Set<Unit> units;
    @DBRef
    private Set<OKRSet> okrSets;

    /**
     * Creates a BusinessUnit with a generated UUID and Sets of units and OkrSets
     *
     * @param units   Set of Units
     * @param okrSets Set of OKRSets
     */
    @PersistenceCreator
    public BusinessUnit(Set<Unit> units, Set<OKRSet> okrSets) {
        this.uuid = UUID.randomUUID();
        this.units = units;
        this.okrSets = okrSets;
    }

    /**
     * Creates a BusinessUnit with a generated UUID and an Array of OKRSets
     *
     * @param okrSets Array of OKRSets
     */
    public BusinessUnit(OKRSet[] okrSets) {
        this.uuid = UUID.randomUUID();
        this.units = new HashSet<>();
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
     * Units Getter
     *
     * @return Set of Units of BusinessUnit
     */

    public Set<Unit> getUnits() {
        return units;
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
     * Adds a Unit u into the Set of Units
     *
     * @param u New Unit added into Set of Units
     */
    public void addUnit(Unit u) {
        this.units.add(u);
    }

    /**
     * OKRSets Setter
     *
     * @param okrSets Set of OKRSets
     */
    public void setOkrSets(Set<OKRSet> okrSets) {
        this.okrSets = okrSets;
    }

    /**
     * Adds an OKRSet into the Set of OKRSets
     *
     * @param okrSet New OKRSet added into Set of Units
     */
    public void addOkrSet(OKRSet okrSet) {
        this.okrSets.add(okrSet);
    }

    /**
     * UUID Setter
     *
     * @param fromString New UUID
     */
    public void setUuid(UUID fromString) {
        this.uuid = fromString;
    }

    /**
     * Override of equals() for BusinessUnit
     *
     * @param obj Compared Object
     * @return Equality of BusinessUnits
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof BusinessUnit other && this.uuid.equals(other.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

}
