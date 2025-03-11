package org.example;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException{
        // URL del DataDase
        String url = "jdbc:mysql://localhost:3306/azienda";

        String user = "root";
        String password = "Password_123";
        String query = "Select * from misure;";

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
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){

                String temperatura = rs.getString("temperatura");
                String umidita = rs.getString("umidita");
                String co2 = rs.getString("co2");

                System.out.println();

                System.out.println(temperatura);
                System.out.println(umidita);
                System.out.println(co2);
            }

            rs.close();
            stmt.close();
            connection.close();

            System.out.println();
            System.out.println("Connessione chiusa con successo");

        } catch (SQLException e) {

            System.out.println("Connessione fallita" + e.getMessage());

        }
    }
}