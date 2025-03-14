package org.example;

import com.fazecast.jSerialComm.*;

public class Main {
    private static StringBuilder buffer = new StringBuilder();
    public static void main(String[] args) throws InterruptedException {
        int i;

        for (i = 0; i < SerialPort.getCommPorts().length; i++) {
            if(SerialPort.getCommPorts()[i].getDescriptivePortName().startsWith("Arduino Uno")) {
                break;
            }
        }

        SerialPort comPort = SerialPort.getCommPort(SerialPort.getCommPorts()[i].getSystemPortName());
        comPort.setComPortParameters(9600, 8, 1, SerialPort.NO_PARITY);
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        comPort.openPort();

        comPort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; }
            @Override
            public void serialEvent(SerialPortEvent event)
            {
                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
                    return;

                byte[] newData = new byte[comPort.bytesAvailable()];
                int numRead = comPort.readBytes(newData, newData.length);

                // Aggiunge i dati al buffer
                for (byte b : newData) {
                    buffer.append((char) b);
                    if (b == '\n') { // Se arriva il terminatore, processa il messaggio
                        System.out.println("Received: " + buffer.toString().trim());
                        buffer.setLength(0); // Svuota il buffer
                    }
                }
            }
        });


        while (true) {
            byte[] sendData = "getTemperature\n".getBytes();
            comPort.writeBytes(sendData, sendData.length);
            Thread.sleep(2000);
        }


    }
}
