package org.example;


import io.javalin.Javalin;
import io.javalin.http.Context;
import org.example.Controller.*;
import org.jetbrains.annotations.NotNull;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Application {

    public static void main(String[] args) {

        DatabaseController.initDatabase();
        Javalin app = Javalin.create(
                javalinConfig -> javalinConfig.router.apiBuilder(() -> {
                    path("/api", () -> {
                        post(Application::ping);
                        post("/register", UserController::registerUser);
                        post("/login", UserController::loginUser);
                        post("/getuserinfo", UserController::userInfo);
                        post("/temperature", BuildController::getTemperature);
                        post("/humidity", BuildController::getHumidity);
                        post("/co2", BuildController::getCO2);

                        path("/arduino", () -> {
                            post("/ping",ArduinoController::ping);
                            post("/register", ArduinoController::registerArduino);
                            post("/add", ArduinoController::addData);
                            post("/generate", ArduinoController::addArduino);

                        });
                        path("/ai", () -> {
                            post("/predict", AIController::predictor);

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