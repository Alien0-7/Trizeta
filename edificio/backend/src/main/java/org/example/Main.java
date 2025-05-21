package org.example;

import com.fazecast.jSerialComm.*;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Scanner;


public class Main {
    static String UUID = "";
    static ArduinoSerialPortDataListener arduinoSerialPortDataListener;
    static SerialPort comPort;

    public static void main(String[] args) {
        if (!initSerialPort())
            return;

        File configFile = new File("src/main/resources/config.properties");
        Properties properties = new Properties();

        initUUID(configFile, properties);

        new SensorDataUploader(comPort, arduinoSerialPortDataListener, UUID).start();

    }

    private static void initUUID(File configFile, Properties properties) {
        //TODO refactoring of this func.

        if (!configFile.exists()) {
            //? finché l'UUID è vuoto e quello inserito non è valido lo richiede all'utente
            while (UUID.isEmpty()) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Inserisci il token: ");
                String UUID_user = scanner.nextLine();

                if (ping(UUID_user))
                    break;
            }

            //? salva il file con l'uuid
            properties.setProperty("uuid", UUID);


            try {
                FileOutputStream fos = new FileOutputStream(configFile);
                properties.store(fos, "config file for this project");
                fos.close();
            } catch (Exception e) {
                System.out.println("Error while writing: " + e);
            }

        } else {
            //? legge se c'è il token e lo salva
            try {
                FileInputStream fis = new FileInputStream(configFile);
                properties.load(fis);
                UUID = properties.getProperty("uuid");

            } catch (Exception ignored) {}

            //? se quello salvato è vuoto e quello inserito non è valido lo richiede all'utente
            while (UUID.isEmpty()) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Inserisci il token: ");
                String UUID_user = scanner.nextLine();

                if (ping(UUID_user))
                    break;
            }

            properties.setProperty("uuid", UUID);

            try {
                FileOutputStream fos = new FileOutputStream(configFile);
                properties.store(fos, "config file for this project");
                fos.close();
            } catch (Exception e) {
                System.out.println("Error while writing: " + e);
            }

        }
    }

    private static boolean ping(String UUID_user) {
        HttpResponse<String> response = Unirest.post("http://trizeta.duckdns.org:10001/api/arduino/ping")
                .field("uuid", UUID_user)
                .asString();

        if (response.getStatus() == 200) {
            UUID = UUID_user;
            return true;
        }

        System.out.println("Il token inserito è sbagliato!");
        return false;
    }

    private static boolean initSerialPort() {
        String portPath = "";

        for (int i = 0; i < SerialPort.getCommPorts().length; i++) {
            System.out.println(SerialPort.getCommPorts()[i].getSystemPortName());
            if (SerialPort.getCommPorts()[i].getDescriptivePortName().startsWith("Arduino Uno")) {
                portPath = SerialPort.getCommPorts()[i].getSystemPortName();
            } else if (SerialPort.getCommPorts()[i].getDescriptivePortName().startsWith("Serial Device")) {
                portPath = "/dev/" + SerialPort.getCommPorts()[i].getSystemPortName();
            }
        }

        if (portPath.isEmpty()) {
            System.out.println("Errore nel trovare la giusta porta di arduino!");
            return false;
        }

        System.out.println("PortPath Selezionata: " + portPath);
        comPort = SerialPort.getCommPort(portPath);
        comPort.setComPortParameters(9600, 8, 1, SerialPort.NO_PARITY);
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        arduinoSerialPortDataListener = new ArduinoSerialPortDataListener(comPort);
        comPort.addDataListener(arduinoSerialPortDataListener);
        comPort.flushIOBuffers();

        comPort.openPort();
        try {
            Thread.sleep(3000);
        } catch (Exception ignored) {}

        return true;
    }
}