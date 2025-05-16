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

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



public class DatabaseController {
    private static final Logger log = LoggerFactory.getLogger(DatabaseController.class);
    private static String DBUser, DBPassword, url, table, columnPassword, columnEmail, columnUUID, columnName, columnSurname, columnAddress;

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
            properties.setProperty("columnName","");
            properties.setProperty("columnSurname","");
            properties.setProperty("columnAddress","");

            try {
                FileOutputStream fos = new FileOutputStream(configFile);
                properties.store(fos, "config file for this project");
                fos.close();
            } catch (Exception e) {
                log.error("Error while writing: " + e);
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
                columnName = properties.getProperty("columnName");
                columnSurname = properties.getProperty("columnSurname");
                columnAddress = properties.getProperty("columnAddress");

            } catch (Exception ignored) {}
        }

        try{

            Class.forName("com.mysql.jdbc.Driver");
            log.info("Driver caricati con successo");

        } catch (ClassNotFoundException e) {
            //! block all req. for the database
            log.error(e.getMessage());

        }

        // TODO (optional) if table doesn't exists create it

    }
    // TODO Connect getDataRequests() to a real "requests" table in the database
    // TODO Handle SQL exceptions more specifically and return *meaningful* error messages
    // TODO Sanitize and validate input data to ensure safety, even with prepared statements

    public static Boolean addUser(User user) {

        try {
            Connection connection = DriverManager.getConnection(url, DBUser, DBPassword);
            log.info("Connesso con il DataBase");
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("Select * from "+table+";");

            while (rs.next()) {

                String email2 = rs.getString(columnEmail);

                if (user.getEmail().equals(email2)) {

                    return false;

                }

            }

            if(user.getName().equalsIgnoreCase("") || user.getName() == null || user.getSurname().equalsIgnoreCase("") || user.getSurname() == null || user.getAddress().equalsIgnoreCase("") || user.getAddress() == null){

                return false;

            }

            int rows = stmt.executeUpdate("insert into "+table+"("+columnUUID+", " +columnEmail+", "+columnPassword+" , "+columnName+", "+columnSurname+", "+columnAddress+") " +
                    "values('"+ UUIDUtils.uuidToBytes(user.getUUID()) +"', '"+user.getEmail()+"', '"+hashPassword(user.getPassword())+"', '"+user.getName()+"', '"+user.getSurname()+"', '"+user.getAddress()+"');");

            if (rows > 0) {

                log.info("Inserimento riuscito di " + rows + " righe");

            } else {

                log.error("Inserimento fallito");
                return false;

            }


            rs.close();
            stmt.close();
            connection.close();

            log.info("Connessione chiusa con successo");
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
            log.info("Connesso con il DataBase");
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("Select * from "+table+";");

            while (rs.next()) {
                String currentEmail = rs.getString(columnEmail);
                String currentPassword = rs.getString(columnPassword);

                if(userEmail.equals(currentEmail) && hashPassword(userPassword).equals(currentPassword)) {
                    User user = new User(currentEmail, currentPassword, null, null, null, UUIDUtils.bytesToUUID(rs.getBytes(columnUUID)));
                    rs.close();
                    stmt.close();
                    connection.close();
                    log.info("Connessione chiusa con successo, Utente trovato");
                    return user;
                }

                log.info(currentEmail);
                log.info(currentPassword);
            }

            rs.close();
            stmt.close();
            connection.close();

            log.info("Connessione chiusa con successo, Utente non trovato");

        } catch (SQLException e) {

            log.error("Connessione fallita" + e.getMessage());

        }

        return null;
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Errore durante l'hashing della password", e);
        }
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