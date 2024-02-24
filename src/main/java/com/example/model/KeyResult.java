package com.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonCreator;


//import java.util.Set;
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
    private String statusUpdate;
    private String description;
    @DBRef
    private OKRSet okrSet;

    /**
     * Creates a new KeyResult with generated UUID
     *
     * @param name         Name of Keyresult
     * @param fulfilled    Fulfillment of KeyResult
     * @param current      Current status of KeyResult
     * @param goal         Goal of KeyResult
     * @param confidence   Confidence of achievable Status of KeyResult
     * @param statusUpdate Current Status of KeyResult
     * @param description  Description of KeyResult
     */
    public KeyResult(String name, short fulfilled, double current, double goal, double confidence, String statusUpdate, String description) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.fulfilled = fulfilled;
        this.current = current;
        this.goal = goal;
        this.confidence = confidence;
        this.statusUpdate = statusUpdate;
        this.description = description;
        this.okrSet = new OKRSet();
    }

    /**
     * Creates a new KeyResult with generated UUID
     *
     * @param name         Name of KeyResult
     * @param fulfilled    Fulfilment
     * @param current      Current status of KeyResult
     * @param goal         Goal of KeyResult
     * @param confidence   Confidence of achievable Status of KeyResult
     * @param statusUpdate Current Status of KeyResult
     * @param description  Description of KeyResult
     * @param okrSet       OkrSet of KeyResult
     */
    @PersistenceCreator
    public KeyResult(String name, short fulfilled, double current, double goal, double confidence, String statusUpdate, String description, OKRSet okrSet) {
        this(name, fulfilled, current, goal, confidence, statusUpdate, description);
        this.okrSet = okrSet;
    }

    /**
     * KeyResult with generated UUID using Json
     */
    @JsonCreator
    public KeyResult() {
        this.uuid = UUID.randomUUID();
    }


    /**
     * UUID Getter
     *
     * @return UUID of KeyResult
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Name Getter
     *
     * @return Name of KeyResult
     */
    public String getName() {
        return name;
    }

    /**
     * Fulfilled Getter
     *
     * @return Status of Fulfilled of KeyResult
     */
    public short getFulfilled() {
        return fulfilled;
    }

    /**
     * Current Getter
     *
     * @return double of current value of KeyResult
     */
    public double getCurrent() {
        return current;
    }

    /**
     * Goal Getter
     *
     * @return double of goal value of KeyResult
     */
    public double getGoal() {
        return goal;
    }

    /**
     * Confidence Getter
     *
     * @return double of confident value of KeyResult
     */
    public double getConfidence() {
        return confidence;
    }

    /**
     * StatusUpdate Getter
     *
     * @return StatusUpdate of KeyResult
     */
    public String getStatusUpdate() {
        return statusUpdate;
    }

    /**
     * Description Getter
     *
     * @return Description of KeyResult
     */
    public String getDescription() {
        return description;
    }

    /**
     * OKRSet Getter
     *
     * @return OkrSet of KeyResult
     */
    public OKRSet getOkrSet() {
        return okrSet;
    }

    /**
     * FulFilled Setter
     *
     * @param fulfilled New Fulfilment Status for KeyResult
     */
    public void setFulfilled(short fulfilled) {
        this.fulfilled = fulfilled;
    }

    /**
     * Current Setter
     *
     * @param current New Current value of KeyResult
     */
    public void setCurrent(double current) {
        this.current = current;
    }

    /**
     * Goal Setter
     *
     * @param goal New Goal value for KeyResult
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
     * @param okrSet New OKRSet for KeyResult
     */
    public void setOkrSet(OKRSet okrSet) {
        this.okrSet = okrSet;
    }

    /**
     * UUID Setter
     *
     * @param fromString New UUID for KeyResult
     */
    public void setUuid(UUID fromString) {
        this.uuid = fromString;
    }

    /**
     * Override of equals() for KeyResult
     *
     * @param obj Compared Object
     * @return Equality of KeyResults
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof KeyResult other && this.uuid.equals(other.uuid);
    }
}
