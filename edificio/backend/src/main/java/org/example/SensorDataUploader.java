package org.example;

import com.fazecast.jSerialComm.SerialPort;
import okhttp3.*;

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
        String type = "";
        switch (str.split(";")[0]) {
            case "getTemperature":
                type = "T";
                break;
            case "getHumidity":
                type = "H";
                break;
            case "getCo2":
                type = "C";
                break;
        }

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("uuid", UUID)
                .addFormDataPart("data_type", type)
                .addFormDataPart("value", str.split(";")[1])
                .addFormDataPart("room", "bagno")
                .build();

        // Richiesta HTTP
        Request request = new Request.Builder()
                .url("http://127.0.0.1:7070/api/arduino/add")  // Cambia URL
                .post(requestBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            response.close();

        } catch (Exception e) {}
    }
}
