package org.example;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException{
        // URL del DataDase
        //String url = "jdbc:mysql://192.168.5.8:3307/4AI_ROSSATO";
        String url = "jdbc:mysql://80.20.95.170:3307/4AI_ROSSATO";

        String user = "4AI_ROSSATO";
        String password = "123456";
        String query = "Select * from login;";

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

                String e = rs.getString("email");

                StringBuilder email = new StringBuilder();

                for( int i = 7; i <= (e.length() - 3); i ++){

                    char a = e.charAt(i);

                    email.append(a);

                }

                String p = rs.getString("password");

                StringBuilder pass = new StringBuilder();

                for( int i = 7; i <= (p.length() - 3); i ++){

                    char a = p.charAt(i);

                    pass.append(a);

                }

                System.out.println();

                System.out.println(e);
                System.out.println(p);
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