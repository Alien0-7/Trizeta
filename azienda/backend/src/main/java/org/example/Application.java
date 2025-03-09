package org.example;


import io.javalin.Javalin;

public class Application {

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7070);

        if (!testConnectionWithArduino(app)) {
            //Messaggio di errore
            app.stop();
        }


    }

    public static boolean testConnectionWithArduino(Javalin app){
        HandlerArduino handlerArduino = new HandlerArduino();
        app.before(handlerArduino);
        return handlerArduino.isConnected();
    }
}