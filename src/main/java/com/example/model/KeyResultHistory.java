package com.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;
import java.util.UUID;

@Document(collection = "KeyResultHistory")
public class KeyResultHistory {
    @Id
    UUID uuid;
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
    @DBRef
    private OKRSet okrSet;

    public KeyResultHistory(String name, short fulfilled, double current, double goal, double confidence, User owner, String statusUpdate, String description, Set<Unit> contributingUnits) {
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

    public KeyResultHistory(KeyResult keyResult){
        this.uuid = UUID.randomUUID();
        this.name = keyResult.getName();
        this.fulfilled = keyResult.getFulfilled();
        this.current = keyResult.getCurrent();
        this.goal = keyResult.getGoal();
        this.confidence = keyResult.getConfidence();
        this.owner = keyResult.getOwner();
        this.statusUpdate = keyResult.getStatusUpdate();
        this.description = keyResult.getDescription();
        this.contributingUnits = keyResult.getContributingUnits();
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

    public void setOkrSet(OKRSet okrSet) {
        this.okrSet = okrSet;
    }

    public void addContributingUnit(Unit u){
        this.contributingUnits.add(u);
    }

}
