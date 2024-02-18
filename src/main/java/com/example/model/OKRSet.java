package com.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonCreator;

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

    @PersistenceCreator
    public OKRSet(Objective objective,List<KeyResult> keyResults) {
        this.uuid = UUID.randomUUID();
        this.objective = objective;
        this.keyResults = keyResults;

    }

    @JsonCreator
    public OKRSet() {
        this.uuid = UUID.randomUUID();
    }

    public OKRSet(Objective objective, KeyResult... keyResults) {
        this.uuid = UUID.randomUUID();
        this.objective = objective;
        this.keyResults = List.of(keyResults);
    }

    public UUID getUuid() {
        return uuid;
    }

    public Objective getObjective() {
        return objective;
    }

    public List<KeyResult> getKeyResults() {
        return keyResults;
    }

    public void setUuid(UUID fromString) {
        this.uuid = fromString;
    }

    public void setObjective(Objective objective) {
        this.objective = objective;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OKRSet) {
            OKRSet other = (OKRSet) obj;
            if(this.uuid.equals(other.uuid)){
                return true;
            }
        }
        return false;
    }

}
