package org.example;

import java.util.UUID;

public class User {
    private String email, password;
    private UUID uuid;

    public User(String email, String password, UUID uuid) {
        this.email = email;
        this.password = password;
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UUID getUUID() {
        return uuid;
    }

    // TODO Add fields such as name, surname, registration date, etc.
}
