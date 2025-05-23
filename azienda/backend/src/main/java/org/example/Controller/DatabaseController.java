package org.example.Controller;


import org.example.User;
import org.example.Utils.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class DatabaseController {
    private static final Logger log = LoggerFactory.getLogger(DatabaseController.class);
    private static String DBUser;
    private static String DBPassword;
    private static String url;
    private static String table_user;
    private static String table_room;
    private static String table_measurement;
    private static String table_actuator;
    private static String columnPassword;
    private static String columnEmail;
    private static String columnUUID;
    private static String columnName_user;
    private static String columnSurname_user;
    private static String columnAddress_user;
    private static String columnName_room;
    private static String columnFk_email_room;
    private static String columnId_room_room;
    private static String issuer;
    private static String error;

    public static void initDatabase() {

        File configFile = new File("src/main/resources/config.properties");
        Properties properties = new Properties();

        if (!configFile.exists()) {
            properties.setProperty("user","");
            properties.setProperty("password","");
            properties.setProperty("url","");
            properties.setProperty("table_user","");
            properties.setProperty("table_room","");
            properties.setProperty("table_measurement","");
            properties.setProperty("table_actuator","");
            properties.setProperty("columnPassword","");
            properties.setProperty("columnEmail","");
            properties.setProperty("columnUUID","");
            properties.setProperty("columnName_user","");
            properties.setProperty("columnSurname_user","");
            properties.setProperty("columnAddress_user","");
            properties.setProperty("columnName_room","");
            properties.setProperty("columnFk_email_room","");
            properties.setProperty("columnId_room_room","");
            properties.setProperty("issuer","");

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
                table_user = properties.getProperty("table_user");
                table_room = properties.getProperty("table_room");
                table_measurement = properties.getProperty("table_measurement");
                table_actuator = properties.getProperty("table_actuator");
                columnPassword = properties.getProperty("columnPassword");
                columnEmail = properties.getProperty("columnEmail");
                columnUUID = properties.getProperty("columnUUID");
                columnName_user = properties.getProperty("columnName_user");
                columnSurname_user = properties.getProperty("columnSurname_user");
                columnAddress_user = properties.getProperty("columnAddress_user");
                columnName_room = properties.getProperty("columnName_room");
                columnFk_email_room = properties.getProperty("columnFk_email_room");
                columnId_room_room = properties.getProperty("columnId_room_room");
                issuer = properties.getProperty("issuer");

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
            ResultSet rs = stmt.executeQuery("Select * from "+table_user+";");

            while (rs.next()) {

                String email2 = rs.getString(columnEmail);

                if (user.getEmail().equals(email2)) {

                    error = "Email already existed";
                    return false;

                }

            }

            if(user.getName().equalsIgnoreCase("") || user.getName() == null || user.getSurname().equalsIgnoreCase("") || user.getSurname() == null || user.getAddress().equalsIgnoreCase("") || user.getAddress() == null){

                error = "invalid value";
                return false;

            }

            int rows = stmt.executeUpdate("insert into "+table_user+"("+columnUUID+", " +columnEmail+", "+columnPassword+" , "+ columnName_user +", "+ columnSurname_user +", "+columnAddress_user+") " +
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

    public static Boolean addArduino(String room,String uuid, String data_type, double value) {

        try {
            Connection connection = DriverManager.getConnection(url, DBUser, DBPassword);
            log.info("Connesso con il DataBase");
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("Select * from "+table_user+";");
            String email = "";
            String id_room = "";
            int count = 0;

            while (rs.next()) {

                String uuid2 = UUIDUtils.bytesToUUID(rs.getBytes(columnUUID)).toString();

                email = rs.getString(columnEmail);

                if (uuid.equals(uuid2)) {

                    rs = stmt.executeQuery("Select * from "+table_room+";");

                    while (rs.next()) {

                        if(room.equalsIgnoreCase(rs.getString(columnName_room)) && email.equalsIgnoreCase(rs.getString(columnFk_email_room))){

                            id_room = rs.getString(columnId_room_room);

                            int rows = stmt.executeUpdate("insert into "+table_measurement+"("+"value" + ", "+ "type" +", "+ "fk_room" +") " +
                                    "values('"+ value + "', '"+data_type+"', '"+id_room+"');");

                            if (rows > 0) {

                                log.info("Inserimento riuscito di " + rows + " righe");
                                return true;

                            }

                        }

                        count++;

                    }

                    int rows = stmt.executeUpdate("insert into "+table_room+"("+columnName_room+", " +"floor"+", "+columnFk_email_room +") " +
                            "values('"+ room +"', '"+1+"', '"+email+"');");

                    rows = stmt.executeUpdate("insert into "+table_measurement+"("+"value"+", " +"date_measurement"+", "+"time_measurement"+" , "+ "type" +", "+ "fk_room" +") " +
                            "values('"+ value +"', '"+"2025-05-22"+"', '"+"2025-05-22 14:30:00"+"', '"+data_type+"', '"+(count + 1)+"');");

                    if (rows > 0) {

                        log.info("Inserimento riuscito di " + rows + " righe");
                        return true;

                    }

                }

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

        return false;

    }

    public static User searchUser(String userEmail, String userPassword) {
        //! ensure email and password are not null
        try {
            Connection connection = DriverManager.getConnection(url, DBUser, DBPassword);
            log.info("Connesso con il DataBase");
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("Select * from "+table_user+";");

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

    public static ArrayList date(String typee, String userEmail) {
        try {
            Connection connection = DriverManager.getConnection(url, DBUser, DBPassword);
            log.info("Connesso con il DataBase");
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("Select * from "+table_user+";");
            boolean found = false;
            ArrayList<Double> values = new ArrayList<>();

            while (rs.next()) {

                String currentEmail = rs.getString(columnEmail);
                if(userEmail.equals(currentEmail)) {

                    found = true;
                    log.info("Utente trovato");
                }

            }

            if(found){
                rs = stmt.executeQuery("Select * from "+table_measurement+";");

                while (rs.next()) {
                    String type = rs.getString("type");
                    double value = rs.getDouble("value");

                    if(type.equalsIgnoreCase(typee)){

                        values.add(value);

                    }

                }

                rs.close();
                stmt.close();
                connection.close();
                log.info("Connessione chiusa con successo, Trasferimento effettuato");
                return values;

            }else{

                rs.close();
                stmt.close();
                connection.close();
                log.info("Connessione chiusa con successo, Utente non trovato");

            }

        } catch (SQLException e) {

            log.error("Connessione fallita" + e.getMessage());

        }
        return null;
    }

    public static Boolean uuidExists(String uuid) {

        try {
            Connection connection = DriverManager.getConnection(url, DBUser, DBPassword);
            log.info("Connesso con il DataBase");
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("Select * from "+table_user+";");

            while (rs.next()) {

                String uuid2 = UUIDUtils.bytesToUUID(rs.getBytes(columnUUID)).toString();

                log.info(uuid2);

                if (uuid.equals(uuid2)) {

                    log.info("UUID trovato");
                    return true;

                }

            }

            rs.close();
            stmt.close();
            connection.close();

            log.info("Connessione chiusa con successo");
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }

        return false;
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

    public static String getIssuer() {
        return issuer;
    }

    public static String getError() {
        return error;
    }
}