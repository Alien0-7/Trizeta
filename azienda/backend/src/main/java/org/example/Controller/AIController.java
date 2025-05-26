package org.example.Controller;

import java.util.ArrayList;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.javalin.http.Context;
import org.example.Utils.SimpleDataPoint;
import org.example.ai.AI;
import org.example.ai.common.RandomSingleton;
import org.example.ai.neuralNetwork.InputType;
import org.example.ai.predictor.DataPoint;
import org.example.ai.predictor.VisualizationType;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AIController {
    private static final Logger log = LoggerFactory.getLogger(AIController.class);

    public static void predictor(@NotNull Context ctx) {
        try {
            ArrayList<DataPoint> data = readFromDB(ctx);

            if (data == null) {
                ctx.status(400);
                ctx.json(Map.of("Error", "Error while fetching form parameters"));
                return;
            }

            AI ai = new AI(data);
            ctx.status(200);
            ctx.json(Map.of("predicted_values", ai.getPredictedData(ctx.formParam("fromDate"))));
        } catch (JWTVerificationException e) {
            ctx.status(400);
            ctx.json(Map.of("Error", "Error while trying to verificate JWT token"));
        } catch (Exception e) {
            ctx.status(400);
            ctx.json(Map.of("Error", "Error while trying to generate predicted values"));
        }
    }

    public static ArrayList<DataPoint> readFromDB(Context ctx) throws JWTVerificationException {
        String SECRET_KEY = "passwordSicuraSegreta";
        String ISSUER = DatabaseController.getIssuer();

        String token = ctx.formParam("token");
        String data_type = ctx.formParam("data_type");
        String fromDate =  ctx.formParam("fromDate");
        String toDate =  ctx.formParam("toDate");

        if (token == null || data_type == null || fromDate == null || toDate == null)
            return null;

        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build();

        DecodedJWT jwt = verifier.verify(token);

        InputType inputType;
        VisualizationType visualizationType;

        if (data_type.equals("T")){
            inputType = InputType.TEMPERATURE;
            visualizationType = VisualizationType.TEMPERATURE;
        } else if (data_type.equals("H")) {
            inputType = InputType.HUMIDITY;
            visualizationType = VisualizationType.HUMIDITY;
        } else {
            inputType = InputType.CO2;
            visualizationType = VisualizationType.CO2;
        }

        ArrayList<SimpleDataPoint> simpleDataPoints = DatabaseController.getUserMeasurements(data_type, jwt.getClaim("uuid").asString(), fromDate, toDate);
        ArrayList<DataPoint> data = new ArrayList<>();
        for (SimpleDataPoint simpleData : simpleDataPoints)
            data.add(new DataPoint(inputType, visualizationType, simpleData.getTimeStr(), (float) simpleData.getValue()));

        return data;
    }

    public static ArrayList<DataPoint> generateRandom(InputType inputType, VisualizationType visualizationType) {
        ArrayList<DataPoint> results = new ArrayList<>();
        results.add(new DataPoint(inputType, visualizationType, 0, RandomSingleton.randFloat(20, 30)));

        for(int i=1;i<24*60;i++) {
            results.add(new DataPoint(inputType, visualizationType, i,
                    results.get(i-1).getValue()
                            + RandomSingleton.randFloat(0, 0.4f)
                            + RandomSingleton.randFloat(0, 0.1f)
                            - RandomSingleton.randFloat(0, 0.4f)
                            - RandomSingleton.randFloat(0, 0.1f) ));

        }

        return results;
    }
}