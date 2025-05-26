package org.example.Controller;

import java.util.ArrayList;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.javalin.http.Context;
import org.example.Utils.Measurement;
import org.example.ai.AI;
import org.example.ai.common.RandomSingleton;
import org.example.ai.neuralNetwork.Temperature;
import org.jetbrains.annotations.NotNull;

public class AIController {

    public static void predictor(@NotNull Context ctx) {
        try {
            ArrayList<Temperature> temps = readFromDB(ctx);
            if (temps == null) {
                ctx.status(200);
                ctx.json(Map.of("Error", "Error while fetching form parameters"));
            }

            AI ai = new AI(temps);
            ctx.status(200);
            ctx.json(Map.of("predicted_temps", ai.evaluate()));
        } catch (JWTVerificationException e) {
            ctx.status(400);
            ctx.json(Map.of("Error", "Error while trying to verificate JWT token"));
        } catch (Exception e) {
            ctx.status(400);
            ctx.json(Map.of("Error", "Error while trying to generate predicted temperatures"));
        }
    }

    public static ArrayList<Temperature> readFromDB(Context ctx) throws JWTVerificationException {
        String SECRET_KEY = "passwordSicuraSegreta";
        String ISSUER = DatabaseController.getIssuer();

        String token = ctx.formParam("token");
        String data_type = ctx.formParam("data_type");
        String fromDate =  ctx.formParam("fromDate");
        String toDate =  ctx.formParam("toDate");

        if (token == null || data_type == null || fromDate == null)
            return null;

        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build();

        DecodedJWT jwt = verifier.verify(token);


        ArrayList<Measurement> measurements = DatabaseController.getUserMeasurements(data_type, jwt.getClaim("uuid").asString(), fromDate, toDate);
        ArrayList<Temperature> temps = new ArrayList<>();
        for (Measurement m : measurements)
            temps.add(new Temperature(m.getTime(), (float) m.getValue()));

        return temps;
    }

    public static ArrayList<Temperature> generateRandom() {
        ArrayList<Temperature> results = new ArrayList<>();
        results.add(new Temperature(0, RandomSingleton.randFloat(10, 30)));

        for(int i=1;i<24*60;i++) {
            results.add(new Temperature(i,
                    results.get(i-1).getValue()
                            + RandomSingleton.randFloat(0, 0.4f)
                            + RandomSingleton.randFloat(0, 0.1f)
                            - RandomSingleton.randFloat(0, 0.4f)
                            - RandomSingleton.randFloat(0, 0.1f)));
        }

        return results;
    }
}