package com.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document(collection = "OKRSet")
public class OKRSet {

    @Id
    private UUID uuid;
    @DBRef
    private Objective objective;
    @DBRef
    private List<KeyResult> keyResults;

    /**
     * Creates an OKRSet with generated UUID
     *
     * @param objective  Objective of OKRSet
     * @param keyResults List of KeyResults of OKRSet
     */
    @PersistenceCreator
    public OKRSet(Objective objective, List<KeyResult> keyResults) {
        this.uuid = UUID.randomUUID();
        this.objective = objective;
        this.keyResults = keyResults;

    }

    /**
     * Creates an OKRSet with generated UUID  using Json
     */
    @JsonCreator
    public OKRSet() {
        this.uuid = UUID.randomUUID();
        List<KeyResult> keyResults = new ArrayList<>();
        this.keyResults = keyResults;
    }

    /**
     * Creates an OKRSet with generated UUID
     *
     * @param objective  Objective of OKRSet
     * @param keyResults Array of KeyResults of OKRSet
     */
    public OKRSet(Objective objective, KeyResult... keyResults) {
        this.uuid = UUID.randomUUID();
        this.objective = objective;
        List<KeyResult> keyResultList = new ArrayList<>();
        for (KeyResult keyResult : keyResults) {
            keyResultList.add(keyResult);
        }
        this.keyResults = keyResultList; 
    }

    /**
     * UUID Getter
     *
     * @return UUID of OKRSet
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Objective Getter
     *
     * @return Objective of OKRSet
     */
    public Objective getObjective() {
        return objective;
    }

    /**
     * KeyResults Getter
     *
     * @return List of KeyResults of OKRSet
     */
    public List<KeyResult> getKeyResults() {
        return keyResults;
    }

    /**
     * UUID Setter
     *
     * @param fromString New UUID for OKRSet
     */
    public void setUuid(UUID fromString) {
        this.uuid = fromString;
    }

    /**
     * Objective Setter
     *
     * @param objective New Objective for OKRSet
     */
    public void setObjective(Objective objective) {
        this.objective = objective;
    }

    /**
    *Adds new KeyResult to OKRSet, but only if there are less than 5
    *
    *@param keyResult Adds new KeyResult to List of KeyResult in OKRSet
    *@return Success of adding a new KeyResult to List
    */
    public boolean addKeyResult(KeyResult keyResult) {
        if(keyResults.size() < 5)
            return keyResults.add(keyResult);
        return false;
    }

    /**
     * Override of equals() for OKRSet
     *
     * @param obj Compared Object
     * @return Equality of OKRSets
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof OKRSet other && this.uuid.equals(other.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

}
