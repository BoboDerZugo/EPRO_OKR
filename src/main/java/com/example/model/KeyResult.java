package com.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;
import java.util.UUID;

@Document(collection = "KeyResult")
public class KeyResult {

    @Id
    private UUID uuid;
    @Indexed
    private String name;
    private short fulfilled;
    private double current;
    private double goal;
    private double confidence;
    private User owner;
    private String statusUpdate;
    private String description;
    @DBRef
    private Set<Unit> contributingUnits;
    //Optional reference to BusinessUnit OKR
    private OKRSet okrSet;

    public KeyResult(String name, short fulfilled, double current, double goal, double confidence, User owner, String statusUpdate, String description, Set<Unit> contributingUnits) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.fulfilled = fulfilled;
        this.current = current;
        this.goal = goal;
        this.confidence = confidence;
        this.owner = owner;
        this.statusUpdate = statusUpdate;
        this.description = description;
        this.contributingUnits = contributingUnits;
    }

    public KeyResult(String name, short fulfilled, double current, double goal, double confidence, User owner, String statusUpdate, String description, Set<Unit> contributingUnits, OKRSet okrSet) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.fulfilled = fulfilled;
        this.current = current;
        this.goal = goal;
        this.confidence = confidence;
        this.owner = owner;
        this.statusUpdate = statusUpdate;
        this.description = description;
        this.contributingUnits = contributingUnits;
        this.okrSet = okrSet;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public short getFulfilled() {
        return fulfilled;
    }

    public double getCurrent() {
        return current;
    }

    public double getGoal() {
        return goal;
    }

    public double getConfidence() {
        return confidence;
    }

    public User getOwner() {
        return owner;
    }

    public String getStatusUpdate() {
        return statusUpdate;
    }

    public String getDescription() {
        return description;
    }

    public Set<Unit> getContributingUnits() {
        return contributingUnits;
    }

    public OKRSet getOkrSet() {
        return okrSet;
    }

    public void setFulfilled(short fulfilled) {
        this.fulfilled = fulfilled;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public void setGoal(double goal) {
        this.goal = goal;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public void addContributingUnit(Unit u){
        this.contributingUnits.add(u);
    }

    public void setOkrSet(OKRSet okrSet) {
        this.okrSet = okrSet;
    }
}
