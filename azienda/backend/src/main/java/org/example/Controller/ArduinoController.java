package org.example.Controller;

import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

public class ArduinoController {
    public static void addData(Context ctx) {

        String uuid = ctx.formParam("uuid");
        String data = ctx.formParam("data_type");
        String rooom = ctx.formParam("room");
        double value = Double.parseDouble(ctx.formParam("value"));

        if(data.equalsIgnoreCase("T")){



        } else if (data.equalsIgnoreCase("C")) {



        }else{



        }

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

        } else if (!isuuid) {

            ctx.status(400);

        }else{

            ctx.status(500);

        }
    }

}
