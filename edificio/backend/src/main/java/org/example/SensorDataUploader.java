package org.example;

import com.fazecast.jSerialComm.SerialPort;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class SensorDataUploader extends Thread {
    SerialPort comPort;
    ArduinoSerialPortDataListener arduinoSerialPortDataListener;
    String UUID;
    boolean exit = false;

    public SensorDataUploader(SerialPort comPort, ArduinoSerialPortDataListener arduinoSerialPortDataListener, String uuid) {
        this.comPort = comPort;
        this.arduinoSerialPortDataListener = arduinoSerialPortDataListener;
        this.UUID = uuid;
    }




    @Override
    public void run() {
        String[] commands = {"getTemperature","getHumidity","getCo2"};

        while (!exit) {
            for (String command : commands) {
                byte[] sendData = (command + "\n").getBytes();
                comPort.writeBytes(sendData, sendData.length);
                while (true) {
                    if (arduinoSerialPortDataListener.isResFinished()) {
                        saveMisure(command + ";" + arduinoSerialPortDataListener.getRes());
                        break;
                    }
                }
            }
            
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                break;
            }

        }
    }


    public void saveMisure(String str) {
        System.out.println(str.split(";")[0] +" , " + str.split(";")[1]);

        HttpResponse<String> response = Unirest.post("http://trizeta.duckdns.org:10001/api/arduino/add")
                .field("uuid", UUID)
                .field(str.split(";")[0], str.split(";")[1])
                .asString();

        System.out.println("Status: " + response.getStatus());
        if (response.getStatus() != 200)
            exit = true;
    }
}
