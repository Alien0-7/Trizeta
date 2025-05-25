package org.example.Controller;

import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class ArduinoController {
    public static void addData(Context ctx) {

        String uuid = ctx.formParam("uuid");
        String data = ctx.formParam("data_type");
        String rooom = ctx.formParam("room");
        double value = Double.parseDouble(ctx.formParam("value"));

        boolean is_dataAdded = DatabaseController.addDataArduino(rooom,uuid,data,value);

        if (is_dataAdded) {

            ctx.status(200);

        } else if (!is_dataAdded){

            ctx.status(400);

        } else {

            ctx.status(500);

        }

    }

    public static void registerArduino(@NotNull Context ctx) {
        //TODO search email and psw given
        //TODO check if that user exist and if it hasn't already an Arduino associated
        ctx.json(Map.of("uuid", "uuid"));

    }

    public static void ping(@NotNull Context ctx) {
        String uuid = ctx.formParam("uuid");
        boolean uuidExists = DatabaseController.uuidExists(uuid);

        if(uuidExists) {

            ctx.status(200);

        } else if(!uuidExists) {

            ctx.status(400);

        } else {

            ctx.status(500);

        }
    }

    public static void addArduino(@NotNull Context ctx) {
        ctx.status(501);

        /*
        String uuid = "";

        try {
            String SECRET_KEY = "passwordSicuraSegreta";
            String ISSUER = DatabaseController.getIssuer();
            String token = ctx.formParam("token");

            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();

            DecodedJWT jwt = verifier.verify(token);
            uuid = jwt.getClaim("uuid").asString();

        } catch (Exception e) {}



         */
    }
}
