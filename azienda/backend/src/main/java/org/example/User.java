package org.example;

import java.util.UUID;

public class User {
    private String email, password, name, surname, address;
    private UUID uuid;

    public User(String email, String password, String name, String surname, String address, UUID uuid) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }

    public UUID getUUID() {
        return uuid;
    }

    // TODO Add fields such as name, surname, registration date, etc.
}
