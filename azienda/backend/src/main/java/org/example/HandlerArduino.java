package org.example;

import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

public class HandlerArduino implements io.javalin.http.Handler {
    private boolean isConnected;

    public HandlerArduino() {
        this.isConnected = false;
    }

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        isConnected = true; //TODO testa la connessione con arduino
    }

    public boolean isConnected() {
        return isConnected;
    }
}
