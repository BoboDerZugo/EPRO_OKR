package com.example.model;

import java.util.UUID;

public class User {
    private enum Role {CO_ADMIN, BU_ADMIN, NORMAL};
    private Role role;
    private UUID uuid;
    private String name;
}
