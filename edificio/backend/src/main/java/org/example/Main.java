package org.example;

import com.fazecast.jSerialComm.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String portPath = "";

        for (int i = 0; i < SerialPort.getCommPorts().length; i++) {
            if(SerialPort.getCommPorts()[i].getDescriptivePortName().startsWith("Arduino Uno")) {
                portPath = SerialPort.getCommPorts()[i].getSystemPortName();
            } else if (SerialPort.getCommPorts()[i].getDescriptivePortName().startsWith("Serial Device")) {
                portPath = "/dev/" + SerialPort.getCommPorts()[i].getSystemPortName();
            }
        }

        SerialPort comPort = SerialPort.getCommPort(portPath);
        comPort.setComPortParameters(9600, 8, 1, SerialPort.NO_PARITY);
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        ArduinoSerialPortDataListener arduinoSerialPortDataListener = new ArduinoSerialPortDataListener(comPort);
        comPort.addDataListener(arduinoSerialPortDataListener);
        comPort.flushIOBuffers();

        comPort.openPort();
        Thread.sleep(3000);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Inserisci il comando: ");
            String str = scanner.nextLine();

            byte[] sendData = (str+"\n").getBytes();
            comPort.writeBytes(sendData, sendData.length);
            System.out.println("MANDATO!");
            while (true) {
                if (arduinoSerialPortDataListener.isResFinished()) {
                    System.out.println(arduinoSerialPortDataListener.getRes());
                    break;
                }
            }
        }


    }
}