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


}
