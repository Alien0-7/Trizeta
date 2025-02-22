package org.example;

import java.io.*;
import java.net.*;

public class Listener {

    public static void main(String[] args) {
        // Numero di porta
        int port = 28000;

        try {
            // Crea un ServerSocket per ascoltare sulla porta 28000
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server in ascolto sulla porta " + port);

            // Ciclo infinito per mantenere il server in esecuzione
            while (true) {
                // Accetta la connessione del client
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nuovo client connesso");

                // Gestisci la connessione del client
                handleClient(clientSocket);
            }
        } catch (IOException e) {
            System.err.println("Errore: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            StringBuilder request = new StringBuilder();
            String line;
            int contentLength = 0;

            // Leggi gli header fino alla riga vuota
            while (!(line = in.readLine()).isEmpty()) {
                request.append(line).append("\n");
                if (line.startsWith("Content-Length:")) {
                    contentLength = Integer.parseInt(line.substring(15).trim());
                }
            }

            // Leggi il corpo della richiesta in base a Content-Length
            char[] body = new char[contentLength];
            in.read(body, 0, contentLength);
            String jsonBody = new String(body);

            System.out.println("Richiesta HTTP completa:\n" + request.toString());
            System.out.println("Corpo JSON ricevuto:\n" + jsonBody);

            // Invia una risposta base HTTP
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: text/plain");
            out.println("Connection: close");
            out.println("\nDati ricevuti con successo!");

            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Errore nella gestione del client: " + e.getMessage());
        }
    }

}

