package org.example.Controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.javalin.http.Context;

import org.example.User;
import org.example.Utils.EmailValidator;
import org.example.Utils.PasswordValidator;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.JWTVerifier;

import java.sql.*;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public static void registerUser(@NotNull Context ctx) {
        String email1 = ctx.formParam("email");
        String pass1 = ctx.formParam("password1");
        String pass2 = ctx.formParam("password2");
        String name = ctx.formParam("name");
        String surname = ctx.formParam("surname");
        String address = ctx.formParam("address");


        //TODO fix validators for numbers (email validator) and for special characters (password validator)
        if (EmailValidator.isValidEmail(email1) && PasswordValidator.isValidPassword(pass1) && PasswordValidator.isValidPassword(pass2)) {

            if(!pass1.equals(pass2)) {

                ctx.status(400);
                ctx.json(Map.of("Error", "different passwords"));
                return;

            }

            Boolean isUserAdded = DatabaseController.addUser(new User(email1, pass1, name, surname, address,UUID.randomUUID()));
            if (isUserAdded == null) {
                ctx.status(500);
            } else if (isUserAdded){
                ctx.status(200);
            } else {
                ctx.status(400);
                ctx.json(Map.of("Error", DatabaseController.getError()));
            }

        } else {
            ctx.status(400);
            ctx.json(Map.of("Error", "Invalid parameters"));
        }

    }

    public static void loginUser(@NotNull Context ctx) {
        User user = DatabaseController.searchUser(ctx.formParam("email"), ctx.formParam("password"));
        String ISSUER = DatabaseController.getIssuer();
        if (user != null) {
            Algorithm algorithm = Algorithm.HMAC256("passwordSicuraSegreta");
            String jwtToken = JWT.create()
                    .withIssuer(ISSUER)
                    .withClaim("email", user.getEmail())
                    .withClaim("password", user.getPassword())
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 12*60*60*1000L))
                    .sign(algorithm);


            ctx.status(200).json(Map.of("authorization_bearer", jwtToken));
        } else {
            ctx.status(400);
        }

    }


}