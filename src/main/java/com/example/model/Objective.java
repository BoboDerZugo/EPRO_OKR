package com.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "Objective")
public class Objective {
    @Id
    private UUID uuid;

    @Indexed
    private String name;

    private short fulfilled;

    public Objective(String name, short fulfilled) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.fulfilled = fulfilled;
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

    public void setFulfilled(short fulfilled) {
        this.fulfilled = fulfilled;
    }
}
