package com.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;
@Document(collection = "User")
public class User {
    public enum Role {CO_ADMIN, BU_ADMIN, NORMAL}
    @Id
    private UUID uuid;
    private Role role;
    @Indexed
    private String name;

    public User(String name, String role) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.role = Role.valueOf(role);
    }

    public String getRole() {
        switch (role) {
            case CO_ADMIN -> {
                return "Company Admin";
            }
            case BU_ADMIN -> {
                return "Business Unit Admin";
            }
            default -> {
                return "Normal";
            }
        }
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }
}
