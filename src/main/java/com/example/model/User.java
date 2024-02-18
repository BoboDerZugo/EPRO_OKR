package com.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Encrypted;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.UUID;
@Document(collection = "User")
public class User {
    public enum Role {CO_ADMIN, BU_ADMIN, NORMAL}
    @Id
    private UUID uuid;
    private Role role;
    @Indexed(unique = true)
    private String name;

    @Encrypted
    private String password;

    @PersistenceCreator
    public User(String name, String password, Role role) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.password = password;
        this.role = role;
    }

    @JsonCreator
    public User() {
        this.uuid = UUID.randomUUID();
    }

    
    public User(String name, String password, String role) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.password = password;
        this.role = Role.valueOf(role);
    }
    


    public String getRole() {
        return role.toString();
    }
    

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            User other = (User) obj;
            if(this.uuid.equals(other.uuid)){
                return true;
            }
        }
        return false;
    }


}
