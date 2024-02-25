package com.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.access.method.P;

//import java.util.Set;
import java.util.UUID;

@Document(collection = "KeyResultHistory")
public class KeyResultHistory {

    @Id
    private UUID uuid;
    @Indexed
    private String name;
    private short fulfilled;
    private double current;
    private double goal;
    private double confidence;
    private String statusUpdate;
    private String description;
    @DBRef
    private OKRSet okrSet;



    
    /**
     * Creates a new KeyResultHistory with generated UUID
     *
     * @param name         Name of KeyResultHistory
     * @param fulfilled    Fulfillment of KeyResultHistory
     * @param current      Current status of KeyResultHistory
     * @param goal         Goal of KeyResultHistory
     * @param confidence   Confidence of achievable Status of KeyResultHistory
     * @param statusUpdate Current Status of KeyResultHistory
     * @param description  Description of KeyResultHistory
     */
    @PersistenceCreator
    public KeyResultHistory(String name, short fulfilled, double current, double goal, double confidence, String statusUpdate, String description) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.fulfilled = fulfilled;
        this.current = current;
        this.goal = goal;
        this.confidence = confidence;
        this.statusUpdate = statusUpdate;
        this.description = description;
    }

    /**
     * Creates a new KeyResultHistory with generated UUID
     *
     * @param name         Name of KeyResultHistory
     * @param fulfilled    Fulfilment
     * @param current      Current status of KeyResultHistory
     * @param goal         Goal of KeyResultHistory
     * @param confidence   Confidence of achievable Status of KeyResultHistory
     * @param statusUpdate Current Status of KeyResultHistory
     * @param description  Description of KeyResultHistory
     * @param okrSet       OkrSet of KeyResultHistory
     */
    public KeyResultHistory(String name, short fulfilled, double current, double goal, double confidence, String statusUpdate, String description, OKRSet okrSet) {
        this(name, fulfilled, current, goal, confidence, statusUpdate, description);
        this.okrSet = okrSet;
    }

    /**
     * Creates a new KeyResultHistory by archiving a KeyResult
     *
     * @param keyResult KeyResult to be archived
     */
    public KeyResultHistory(KeyResult keyResult) {
        this(keyResult.getName(), keyResult.getFulfilled(), keyResult.getCurrent(), keyResult.getGoal(), keyResult.getConfidence(), keyResult.getStatusUpdate(), keyResult.getDescription(), keyResult.getOkrSet());
    }

    /**
     * UUID Getter
     *
     * @return UUID of KeyResultHistory
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Name Getter
     *
     * @return Name of KeyResultHistory
     */
    public String getName() {
        return name;
    }

    /**
     * Fulfilled Getter
     *
     * @return Status of Fulfilled of KeyResultHistory
     */
    public short getFulfilled() {
        return fulfilled;
    }

    /**
     * Current Getter
     *
     * @return double of current value of KeyResultHistory
     */
    public double getCurrent() {
        return current;
    }

    /**
     * Goal Getter
     *
     * @return double of goal value of KeyResultHistory
     */
    public double getGoal() {
        return goal;
    }

    /**
     * Confidence Getter
     *
     * @return double of confident value of KeyResultHistory
     */
    public double getConfidence() {
        return confidence;
    }

    /**
     * StatusUpdate Getter
     *
     * @return StatusUpdate of KeyResultHistory
     */
    public String getStatusUpdate() {
        return statusUpdate;
    }

    /**
     * Description Getter
     *
     * @return Description of KeyResultHistory
     */
    public String getDescription() {
        return description;
    }

    /**
     * OKRSet Getter
     *
     * @return OkrSet of KeyResultHistory
     */
    public OKRSet getOkrSet() {
        return okrSet;
    }

    /**
     * FulFilled Setter
     *
     * @param fulfilled New Fulfilment Status for KeyResultHistory
     */
    public void setFulfilled(short fulfilled) {
        this.fulfilled = fulfilled;
    }

    /**
     * Current Setter
     *
     * @param current New Current value of KeyResultHistory
     */
    public void setCurrent(double current) {
        this.current = current;
    }

    /**
     * Goal Setter
     *
     * @param goal New Goal value for KeyResultHistory
     */
    public void setGoal(double goal) {
        this.goal = goal;
    }

    /**
     * Confidence Setter
     *
     * @param confidence New Confidence value
     */
    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    /**
     * OKRSet Setter
     *
     * @param okrSet New OKRSet for KeyResultHistory
     */
    public void setOkrSet(OKRSet okrSet) {
        this.okrSet = okrSet;
    }

    /**
     * Override of equals() for KeyResultHistory
     *
     * @param obj Compared Object
     * @return Equality of KeyResultHistories
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof KeyResultHistory other && this.uuid.equals(other.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
    
}
