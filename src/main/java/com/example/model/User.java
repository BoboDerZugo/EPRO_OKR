package com.example.model;

import java.util.UUID;

public class User {
    private enum Role {CO_ADMIN, BU_ADMIN, NORMAL};
    private Role role;
    private UUID uuid;
    private String name;

    public User(String name, String role) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.role = Role.valueOf(role);
    }

    public Role getRole() {
        return role;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }
}
