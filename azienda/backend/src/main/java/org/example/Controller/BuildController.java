package org.example.Controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class BuildController {

    public static void getTemperature(@NotNull Context ctx) {
        Algorithm algorithm = Algorithm.HMAC256("passwordSicuraSegreta");
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("passwordSicuraSegreta")
                .build();

        String jwtToken = ctx.formParam("JWT");
        System.out.println(jwtToken);
        try {
            DecodedJWT decodedJWT = verifier.verify(jwtToken);
            ctx.status(200);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            ctx.status(400);
        }


    }

}
