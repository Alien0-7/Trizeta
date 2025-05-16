package org.example;


import io.javalin.Javalin;
import io.javalin.http.Context;
import org.example.Controller.ArduinoController;
import org.example.Controller.BuildController;
import org.example.Controller.DatabaseController;
import org.example.Controller.UserController;
import org.jetbrains.annotations.NotNull;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Application {

    public static void main(String[] args) {

        DatabaseController.initDatabase();
        Javalin app = Javalin.create(
                //https://javalin.io/documentation#handler-groups
                javalinConfig -> javalinConfig.router.apiBuilder(() -> {
                    path("/api", () -> {
                        post(Application::prova);
                        post("/register", UserController::registerUser);
                        post("/login", UserController::loginUser);
                        post("/temperature", BuildController::getTemperature);

                        path("/arduino", () -> {
                            post("/register", ArduinoController::registerArduino);
                            post("/add", ArduinoController::addData);

                        });
                    });

                    javalinConfig.bundledPlugins.enableCors(cors -> {
                        cors.addRule(it -> {
                            it.anyHost();
                        });
                    });
                })
        ).start(7070);

    }

    public static void prova(@NotNull Context ctx) {
        ctx.status(200);
    }

}