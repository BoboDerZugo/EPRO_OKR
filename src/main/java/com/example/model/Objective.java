package com.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "Objective")
public class Objective {
    @Id
    private UUID uuid;

    @Indexed
    private String name;

    private short fulfilled;

    /**
     * Creates a new Objective with generated UUID
     *
     * @param name      Name of the Objective
     * @param fulfilled Status of the Objective
     */
    @PersistenceCreator
    public Objective(String name, short fulfilled) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.fulfilled = fulfilled;
    }

    /**
     * UUID Getter
     *
     * @return UUID of Objective
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Name Getter
     *
     * @return Name of Objective
     */
    public String getName() {
        return name;
    }

    /**
     * Fulfilled Getter
     *
     * @return Fulfilment Status of Objective
     */
    public short getFulfilled() {
        return fulfilled;
    }

    /**
     * Fulfilled Setter
     *
     * @param fulfilled New fulfilment Status for Objective
     */
    public void setFulfilled(short fulfilled) {
        this.fulfilled = fulfilled;
    }

    /**
     * UUID Setter
     *
     * @param fromString New UUID for Objective
     */
    public void setUuid(UUID fromString) {
        this.uuid = fromString;
    }

    /**
     * Override of equals() for Objective
     *
     * @param obj Compared Object
     * @return Equality of Objectives
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Objective other && this.uuid.equals(other.uuid);
    }
}
