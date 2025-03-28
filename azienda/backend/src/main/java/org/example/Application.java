package org.example;


import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Application {

    public static void main(String[] args) {
        Javalin app = Javalin.create(
                //https://javalin.io/documentation#handler-groups
                javalinConfig -> javalinConfig.router.apiBuilder(() -> {
                    path("/api", () -> {
                        post("/register", UserController::registerUser);
                        post("/login", UserController::loginUser);

                        path("/arduino", () -> {
                            post("/login", ArduinoController::login);
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

//        if (!testConnectionWithArduino(app)) {
//            //Messaggio di errore
//            app.stop();
//        }




    }


//    public static boolean testConnectionWithArduino(Javalin app){
//        HandlerArduino handlerArduino = new HandlerArduino();
//        app.before(handlerArduino);
//        return handlerArduino.isConnected();
//    }


}