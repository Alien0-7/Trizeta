package org.example;


import io.javalin.Javalin;
import org.example.Controller.ArduinoController;
import org.example.Controller.UserController;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Application {

    public static void main(String[] args) {
        //TODO configure property of slf4j

        Javalin app = Javalin.create(
                //https://javalin.io/documentation#handler-groups
                javalinConfig -> javalinConfig.router.apiBuilder(() -> {
                    path("/api", () -> {
                        post("/register", UserController::registerUser);
                        post("/login", UserController::loginUser);

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


}