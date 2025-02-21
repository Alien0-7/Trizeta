import java.io.*;
import java.net.*;

public class Sender {

    public static void main(String[] args) {
        // Indirizzo del server e la porta
        String host = "localhost";  // O l'indirizzo IP del server
        int port = 28000;

        try {
            // Crea una connessione al server
            Socket socket = new Socket(host, port);

            // Stream di output per inviare dati al server
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            // Crea il JSON come stringa
            String json = "{\"name\": \"Alice\", \"age\": 25, \"city\": \"Rome\"}";

            // Invia il JSON al server
            writer.println(json);

            // Chiudi la connessione
            socket.close();
        } catch (IOException e) {
            System.err.println("Errore nella connessione al server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
