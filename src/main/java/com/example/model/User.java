package com.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.UUID;
@Document(collection = "User")
public class User {
    public enum Role {CO_ADMIN, BU_ADMIN, NORMAL}
    @Id
    private UUID uuid;
    private Role role;
    @Indexed
    private String name;

    @PersistenceCreator
    public User(String name, Role role) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.role = role;
    }

    @JsonCreator
    public User() {
        this.uuid = UUID.randomUUID();
    }

    
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

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }


}
