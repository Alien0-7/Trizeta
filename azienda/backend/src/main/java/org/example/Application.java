package org.example;


import io.javalin.Javalin;
import io.javalin.http.Context;
import org.example.Controller.ArduinoController;
import org.example.Controller.BuildController;
import org.example.Controller.DatabaseController;
import org.example.Controller.UserController;
import org.example.Utils.UUIDUtils;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Application {

    public static void main(String[] args) {

        DatabaseController.initDatabase();
        Javalin app = Javalin.create(
                //https://javalin.io/documentation#handler-groups
                javalinConfig -> javalinConfig.router.apiBuilder(() -> {
                    path("/api", () -> {
                        post(Application::ping);
                        post("/register", UserController::registerUser);
                        post("/login", UserController::loginUser);
                        post("/temperature", BuildController::getTemperature);
                        post("/umidity", BuildController::getUmidity);
                        post("/co2", BuildController::getCO2);

                        path("/arduino", () -> {
                            post("/ping",ArduinoController::ping);
                            post("/register", ArduinoController::registerArduino);
                            post("/add", ArduinoController::addData);
                            post("/generate", ArduinoController::addArduino);

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

    public static void ping(@NotNull Context ctx) {
        ctx.status(200);
    }

}