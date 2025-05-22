package org.example.Controller;

import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

public class ArduinoController {
    public static void addData(Context ctx) {
        String uuid = ctx.formParam("uuid");

        System.out.println(ctx.body());

    }

    public static void registerArduino(@NotNull Context ctx) {
        //TODO search email and psw given
        //TODO check if that user exist and if it hasn't already an Arduino associated
        ctx.json(Map.of("uuid", "uuid"));

    }

    public static void ping(@NotNull Context ctx) {
        String uuid = ctx.formParam("uuid");
        boolean isuuid = DatabaseController.uuidExists(uuid);
        if(isuuid){

            ctx.status(200);
            ctx.json(Map.of("uuid", "uuid valido"));

        } else if (!isuuid) {

            ctx.status(400);
            ctx.json(Map.of("uuid", "uuid non valido"));

        }else{

            ctx.status(500);

        }
    }

}
