package org.example;

import com.fazecast.jSerialComm.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        SerialPort comPort = SerialPort.getCommPort("COM4");
        comPort.setComPortParameters(9600, 8, 1, SerialPort.NO_PARITY);
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        comPort.openPort();

        while (true) {
            byte[] sendData = "getTemperature\n".getBytes();
            comPort.writeBytes(sendData, sendData.length);

            Thread.sleep(2000);
            if (comPort.bytesAvailable() > 0) {
                byte[] newData = new byte[comPort.bytesAvailable()]; //crea l'array
                int numRead = comPort.readBytes(newData, newData.length); //legge i byte

                //stampa i byte e poi fa a capo
                for (byte newDatum : newData) {
                    System.out.printf("%02X ",newDatum); //%02X serve per stampare i byte correttamente in HEX e a gruppi di 2
                }
                System.out.println();

                //converte i byte letti in stringa e stampa
                String s = new String(newData);
                System.out.println("Read: " + numRead + " bytes, => " + " String: " + s);
            }
        }



    }
}
