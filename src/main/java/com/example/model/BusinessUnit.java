package com.example.model;

import java.util.Set;
import java.util.UUID;

public class BusinessUnit {
    private UUID uuid;
    private Set<User> employees;
    private Set<Unit> units;
    private OKRSet[] okrSets = new OKRSet[5];
}
