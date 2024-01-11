package com.example.model;

import java.util.UUID;

public class Objective {
    private UUID uuid;
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
