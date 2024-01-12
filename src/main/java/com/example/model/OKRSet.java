package com.example.model;

import jdk.jfr.DataAmount;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;
@Document(collection = "OKRSet")
public class OKRSet {

    @Id
    private UUID uuid;
    @DBRef
    private Objective objective;
    @DBRef
    private KeyResult[] keyResults;
    public OKRSet(Objective objective,KeyResult... keyResults) {
        this.uuid = UUID.randomUUID();
        this.objective = objective;
        this.keyResults = new KeyResult[5];
        int minLength = Math.min(this.keyResults.length, keyResults.length);
        System.arraycopy(keyResults, 0, this.keyResults, 0, minLength);
    }

    public UUID getUuid() {
        return uuid;
    }

    public Objective getObjective() {
        return objective;
    }

    public KeyResult[] getKeyResults() {
        return keyResults;
    }

}
