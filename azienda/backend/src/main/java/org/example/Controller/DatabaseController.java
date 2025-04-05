package org.example.Controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class DatabaseController {
    private static String user, password, url;

    public static void initDatabase() {

        File configFile = new File("config.properties");
        Properties properties = new Properties();

        if (!configFile.exists()) {
            properties.setProperty("user","");
            properties.setProperty("password","");
            properties.setProperty("url","");
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
                user = properties.getProperty("user");
                password = properties.getProperty("password");
                url = properties.getProperty("url");

            } catch (Exception ignored) {}
        }


        // TODO (optional) if table doesn't exists create it

    }

    // TODO Replace printStackTrace with proper logging (SLF4J)
    // TODO Implement secure password storage using hashing (SHA256)
    // TODO Connect getDataRequests() to a real "requests" table in the database
    // TODO Handle SQL exceptions more specifically and return *meaningful* error messages
    // TODO Sanitize and validate input data to ensure safety, even with prepared statements

    public static boolean addUser() {
        return false;
    }

    public static boolean addArduino() {
        return false;
    }

    public static boolean searchUser() {
        return false;
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
}
