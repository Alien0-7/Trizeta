package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Inserimento {
    public static void main(String[] args) throws ClassNotFoundException{
        // URL del DataDase
        String url = "jdbc:mysql://192.168.5.8:3307/4AI_ROSSATO";
        //String url = "jdbc:mysql://80.20.95.170:3307/4AI_ROSSATO";

        String user = "4AI_ROSSATO";
        String password = "123456";
        String query = "insert into login(email, password) values('email@gmail.com', '12345');";

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

                System.out.println("Inserimento riuscito di "+ rows+" righe");

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