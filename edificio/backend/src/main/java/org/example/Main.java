package org.example;

import com.fazecast.jSerialComm.*;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        String portPath = "";

        for (int i = 0; i < SerialPort.getCommPorts().length; i++) {
            System.out.println(SerialPort.getCommPorts()[i].getSystemPortName());
            if(SerialPort.getCommPorts()[i].getDescriptivePortName().startsWith("Arduino Uno")) {
                portPath = SerialPort.getCommPorts()[i].getSystemPortName();
            } else if (SerialPort.getCommPorts()[i].getDescriptivePortName().startsWith("Serial Device")) {
                portPath = "/dev/" + SerialPort.getCommPorts()[i].getSystemPortName();
            }
        }
        if (portPath.isEmpty()) {
            System.out.println("Errore nel trovare la giusta porta di arduino!");
            return;
        }

        System.out.println("PortPath Selezionata: " + portPath);
        SerialPort comPort = SerialPort.getCommPort(portPath);
        comPort.setComPortParameters(9600, 8, 1, SerialPort.NO_PARITY);
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        ArduinoSerialPortDataListener arduinoSerialPortDataListener = new ArduinoSerialPortDataListener(comPort);
        comPort.addDataListener(arduinoSerialPortDataListener);
        comPort.flushIOBuffers();

        comPort.openPort();
        Thread.sleep(3000);

        Thread threadMisure = new Thread(){
            @Override
            public void run() {
                String[] commands = {"getTemperature","getHumidity","getCo2"};

                while (true) {
                    for (int i = 0; i < commands.length; i++) {
                        byte[] sendData = (commands[i] + "\n").getBytes();
                        comPort.writeBytes(sendData, sendData.length);
                        while (true) {
                            if (arduinoSerialPortDataListener.isResFinished()) {
                                store(commands[i]+";"+arduinoSerialPortDataListener.getRes());
                                break;
                            }
                        }

                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {break;}
                }

            }
        };
        threadMisure.start();



    }

    public static void store(String str) {

        System.out.println(str.split(";")[0] +" , " + str.split(";")[1]);
        if (true) { //se già registrato
            HttpResponse<String> response = Unirest.post("http://10.1.6.27:7070/api/arduino/add")
                    .field(str.split(";")[0], str.split(";")[1])
                    .asString();

            System.out.println("Status: " + response.getStatus());
            System.out.println("Body:\n" + response.getBody());

        } else {

        }
    }
}