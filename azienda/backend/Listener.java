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
            // Imposta gli stream per leggere e scrivere con il client
            InputStream input = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            PrintWriter writer = new PrintWriter(output, true);

            // Leggi il messaggio del client (presumiamo sia un JSON)
            StringBuilder jsonMessage = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonMessage.append(line);
            }

            // Stampa il JSON ricevuto
            System.out.println("JSON ricevuto dal client:");
            System.out.println(jsonMessage.toString());

            // Rispondi al client (opzionale)
            writer.println("JSON ricevuto correttamente!");

            // Chiudi la connessione con il client
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Errore nella gestione del client: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

