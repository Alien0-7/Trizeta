package org.example.Controller;

import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

public class ArduinoController {
    public static void addData(Context ctx) {
        String uuid = ctx.formParam("uuid");
        //TODO search if the uuid exist in the database
        //TODO if so, return the data requests

    }

    public static void registerArduino(@NotNull Context ctx) {
        //TODO search email and psw given
        //TODO check if that user exist and if it hasn't already an Arduino associated
        ctx.json(Map.of("uuid", "uuid"));

    }

}
