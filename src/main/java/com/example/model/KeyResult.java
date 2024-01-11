package com.example.model;

import java.util.Set;
import java.util.UUID;

public class KeyResult {

    private UUID uuid;
    private String name;
    private short fulfilled;
    private double current;
    private double goal;
    private double confidence;
    private User owner;
    private String statusUpdate;
    private String description;
    private Set<Unit> contributingUnits;

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
}
