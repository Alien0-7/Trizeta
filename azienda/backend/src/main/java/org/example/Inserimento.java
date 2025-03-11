package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Inserimento {
    public static void main(String[] args) throws ClassNotFoundException{
        // URL del DataDase
        String url = "jdbc:mysql://localhost:3306/azienda";

        String user = "root";
        String password = "Password_123";
        String query = "insert into misure(temperatura, umidita, co2) values('{\\\"1\\\": 35, \\\"2\\\": 45, \\\"3\\\": 40}', '{\\\"1\\\": 24, \\\"2\\\": 35, \\\"3\\\": 12}', '{\\\"1\\\": 65, \\\"2\\\": 55, \\\"3\\\": 50}');";

        try{

            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver caricati con successo");

        } catch (ClassNotFoundException e) {

            System.out.println(e.getMessage());

        }

        try {

            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connesso con il DataBase");
            Statement stmt = connection.createStatement();
            int rows= stmt.executeUpdate(query);

            if(rows > 0){

                System.out.println("Inserimento riuscito"+ rows);

            }else{

                System.out.println("Inserimento fallito");

            }

            stmt.close();
            connection.close();

            System.out.println();
            System.out.println("Connessione chiusa con successo");

        } catch (SQLException e) {

            System.out.println("Connessione fallita" + e.getMessage());

        }
    }
}