package org.example.Controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.javalin.http.Context;
import org.example.Utils.SimpleDataPoint;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;

public class BuildController {

    public static void getTemperature(@NotNull Context ctx) {

        String type = "T";
        String SECRET_KEY = "passwordSicuraSegreta";
        String ISSUER = DatabaseController.getIssuer();

        String token = ctx.formParam("token");
        String fromDate = ctx.formParam("fromDate");
        String toDate =  ctx.formParam("toDate");

        try {

            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();

            DecodedJWT jwt = verifier.verify(token);

            ArrayList<SimpleDataPoint> temperature = DatabaseController.getUserMeasurements(type, jwt.getClaim("uuid").asString(), fromDate, toDate);

            ctx.status(200);
            ctx.json(Map.of("temperature", temperature));

        } catch (JWTVerificationException e) {

            ctx.status(400);
            ctx.json(Map.of("Error", "Token JWT invalido"));

        }

    }

    public static void getHumidity(@NotNull Context ctx) {

        String type = "H";
        String SECRET_KEY = "passwordSicuraSegreta";
        String ISSUER = DatabaseController.getIssuer();

        String token = ctx.formParam("token");
        String fromDate = ctx.formParam("fromDate");
        String toDate =  ctx.formParam("toDate");

        try {

            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();

            DecodedJWT jwt = verifier.verify(token);

            ArrayList<SimpleDataPoint> humidity = DatabaseController.getUserMeasurements(type, jwt.getClaim("uuid").asString(), fromDate, toDate);

            ctx.status(200);
            ctx.json(Map.of("humidity", humidity));

        } catch (JWTVerificationException e) {

            ctx.status(400);
            ctx.json(Map.of("Error", "Token JWT invalido"));

        }

    }

    public static void getCO2(@NotNull Context ctx) {

        String type = "C";
        String SECRET_KEY = "passwordSicuraSegreta";
        String ISSUER = DatabaseController.getIssuer();

        String token = ctx.formParam("token");
        String fromDate = ctx.formParam("fromDate");
        String toDate =  ctx.formParam("toDate");

        try {

            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();

            DecodedJWT jwt = verifier.verify(token);

            ArrayList<SimpleDataPoint> co2 = DatabaseController.getUserMeasurements(type, jwt.getClaim("uuid").asString(), fromDate, toDate);

            ctx.status(200);
            ctx.json(Map.of("Co2", co2));

        } catch (JWTVerificationException e) {

            ctx.status(400);
            ctx.json(Map.of("Error", "Token JWT invalido"));

        }

    }


}
