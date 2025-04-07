package org.example.Controller;


import org.example.User;
import org.example.Utils.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.Arrays;
import java.util.Properties;

public class DatabaseController {
    private static final Logger log = LoggerFactory.getLogger(DatabaseController.class);
    private static String DBUser, DBPassword, url, table, columnPassword, columnEmail, columnUUID;

    public static void initDatabase() {

        File configFile = new File("config.properties");
        Properties properties = new Properties();

        if (!configFile.exists()) {
            properties.setProperty("user","");
            properties.setProperty("password","");
            properties.setProperty("url","");
            properties.setProperty("table","");
            properties.setProperty("columnPassword","");
            properties.setProperty("columnEmail","");
            properties.setProperty("columnUUID","");

            try {
                FileOutputStream fos = new FileOutputStream(configFile);
                properties.store(fos, "config file for this project");
                fos.close();
            } catch (Exception e) {
                System.out.println("Error while writing: " + e);
            }

        } else {
            try {
                FileInputStream fis = new FileInputStream(configFile);
                properties.load(fis);
                DBUser = properties.getProperty("user");
                DBPassword = properties.getProperty("password");
                url = properties.getProperty("url");
                table = properties.getProperty("table");
                columnPassword = properties.getProperty("columnPassword");
                columnEmail = properties.getProperty("columnEmail");
                columnUUID = properties.getProperty("columnUUID");

            } catch (Exception ignored) {}
        }

        try{

            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver caricati con successo");

        } catch (ClassNotFoundException e) {
            //! block all req. for the database
            System.out.println(e.getMessage());

        }

        // TODO (optional) if table doesn't exists create it

    }

    // TODO Replace printStackTrace with proper logging (SLF4J)
    // TODO Implement secure password storage using hashing (SHA256)
    // TODO Connect getDataRequests() to a real "requests" table in the database
    // TODO Handle SQL exceptions more specifically and return *meaningful* error messages
    // TODO Sanitize and validate input data to ensure safety, even with prepared statements

    public static Boolean addUser(User user) {

        try {
            Connection connection = DriverManager.getConnection(url, DBUser, DBPassword);
            System.out.println("Connesso con il DataBase");
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("Select * from "+table+";");

            while (rs.next()) {

                String email2 = rs.getString(columnEmail);

                if (user.getEmail().equals(email2)) {

                    return false;

                }

            }


            int rows = stmt.executeUpdate("insert into "+table+"("+columnUUID+", " +columnEmail+", "+columnPassword+") " +
                    "values('"+ UUIDUtils.uuidToBytes(user.getUUID()) +"', '"+user.getEmail()+"', '"+user.getPassword()+"');");

            if (rows > 0) {

                System.out.println("Inserimento riuscito di " + rows + " righe");

            } else {

                System.out.println("Inserimento fallito");
                return false;

            }


            rs.close();
            stmt.close();
            connection.close();

            System.out.println();
            System.out.println("Connessione chiusa con successo");
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }

        return true;
    }

    public static boolean addArduino() {
        return false;
    }

    public static User searchUser(String userEmail, String userPassword) {
        //! ensure email and password are not null
        try {

            Connection connection = DriverManager.getConnection(url, DBUser, DBPassword);
            System.out.println("Connesso con il DataBase");
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("Select * from "+table+";");

            while (rs.next()) {
                String currentEmail = rs.getString(columnEmail);
                String currentPassword = rs.getString(columnPassword);

                if(userEmail.equals(currentEmail) && userPassword.equals(currentPassword)) {
                    User user = new User(currentEmail, currentPassword, UUIDUtils.bytesToUUID(rs.getBytes(columnUUID)));
                    rs.close();
                    stmt.close();
                    connection.close();
                    System.out.println("Connessione chiusa con successo, Utente trovato");
                    return user;
                }

                System.out.println();

                System.out.println(currentEmail);
                System.out.println(currentPassword);
            }

            rs.close();
            stmt.close();
            connection.close();

            System.out.println();
            System.out.println("Connessione chiusa con successo, Utente non trovato");

        } catch (SQLException e) {

            System.out.println("Connessione fallita" + e.getMessage());

        }

        return null;
    }

    public static boolean searchArduino() {
        return false;
    }

    public static boolean removeUser() {
        return false;
    }

    public static boolean removeArduino() {
        return false;
    }

    //--- Getter


    public static String getDBUser() {
        return DBUser;
    }

    public static String getUrl() {
        return url;
    }

    public static String getDBPassword() {
        return DBPassword;
    }

    public static String getTable() {
        return table;
    }
}
