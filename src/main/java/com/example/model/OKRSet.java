package com.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

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
    public OKRSet(Objective objective,KeyResult... keyResults) {
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

}
