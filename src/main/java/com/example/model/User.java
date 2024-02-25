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

    /**
     * Creates a User with generated UUID
     *
     * @param name     Name of User
     * @param password Password of User
     * @param role     Role of User (Enum)
     */
    @PersistenceCreator
    public User(String name, String password, Role role) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.password = password;
        this.role = role;
    }

    /**
     * Creates a User with Generated UUID  using Json
     */
    @JsonCreator
    public User() {
        this.uuid = UUID.randomUUID();
        this.role = Role.NORMAL;
    }

    /**
     * Creates a User with generated UUID
     *
     * @param name     Name of User
     * @param password Password of User
     * @param role     Role of User (String)
     */
    public User(String name, String password, String role) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.password = password;
        this.role = Role.valueOf(role);
    }


    /**
     * Role Getter
     *
     * @return String of Role of User
     */
    public String getRole() {
        return role.toString();
    }

    /**
     * UUID Getter
     *
     * @return UUID of User
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Name Getter
     *
     * @return Name of User
     */
    public String getName() {
        return name;
    }

    /**
     * Password Getter
     *
     * @return Password of User
     */
    public String getPassword() {
        return password;
    }

    /**
     * Password Setter
     *
     * @param password New Password for User
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * UUID Setter
     *
     * @param uuid New UUID for User
     */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Role Setter
     *
     * @param role New Role for User
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Name Setter
     *
     * @param name New Name for User
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Override of equals() for User
     *
     * @param obj Compared Object
     * @return Equality of Users
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof User other && this.uuid.equals(other.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }


}
