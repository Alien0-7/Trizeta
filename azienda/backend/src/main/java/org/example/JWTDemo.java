package org.example;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JWTDemo {
    public static void main(String[] args) {
        Algorithm algorithm = Algorithm.HMAC256("baeldung");
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("Baeldung")
                .build();

        String jwtToken = JWT.create()
                .withIssuer("Baeldung")
                .withClaim("email", "email@gmail.com")
                .withClaim("password", "password")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 5000L))
                .sign(algorithm);

        try {
            DecodedJWT decodedJWT = verifier.verify(jwtToken);
            System.out.println(decodedJWT.getClaims());
            Thread.sleep(6000L);

            decodedJWT = verifier.verify(jwtToken);
            System.out.println(decodedJWT.getClaims());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
