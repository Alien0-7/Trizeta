package org.example.Controller;

import java.util.ArrayList;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.javalin.http.Context;
import org.example.ai.AI;
import org.example.ai.common.RandomSingleton;
import org.example.Utils.Measurement;
import org.jetbrains.annotations.NotNull;

public class AIController {

    public static void predictor(@NotNull Context ctx) {
        ctx.status(501);
        AI ai = new AI(readFromDB(ctx));

    }

    public static ArrayList<Measurement> readFromDB(Context ctx) {
        String SECRET_KEY = "passwordSicuraSegreta";
        String ISSUER = DatabaseController.getIssuer();

        String token = ctx.formParam("token");
        String uuid = "";
        try {

            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();

            DecodedJWT jwt = verifier.verify(token);

            uuid = jwt.getClaim("uuid").asString();

        } catch (JWTVerificationException e) {

            ctx.status(400);
            ctx.json(Map.of("Error", "Token JWT invalido"));

        }

        return DatabaseController.getUserMeasurements(ctx.formParam("data_type"), uuid, ctx.formParam("fromDate"));
    }

    public static ArrayList<Measurement> generateRandom() {
        ArrayList<Measurement> results = new ArrayList<>();
        results.add(new Measurement(0, RandomSingleton.randFloat(10, 30)));

        for(int i=1;i<24*60;i++) {
            results.add(new Measurement(i,
                    results.get(i-1).getValue()
                            + RandomSingleton.randFloat(0, 0.4f)
                            + RandomSingleton.randFloat(0, 0.1f)
                            - RandomSingleton.randFloat(0, 0.4f)
                            - RandomSingleton.randFloat(0, 0.1f)));
        }

        return results;
    }
}