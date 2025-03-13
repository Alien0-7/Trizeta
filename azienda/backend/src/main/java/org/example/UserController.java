package org.example;

import io.javalin.http.Context;
import org.eclipse.jetty.server.Authentication.User;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class UserController {
    public static void createUser(@NotNull Context ctx) {
        User user = ctx.bodyAsClass(User.class);
        System.out.println("Dati ricevuti per la registrazione: " + user);

        ctx.status(200);
        ctx.json(Map.of("message", "Registrazione avvenuta con successo"));
    }

    public static void loginUser(@NotNull Context ctx) {
        //ctx.bodyAtClass(User.class)
    }
}
