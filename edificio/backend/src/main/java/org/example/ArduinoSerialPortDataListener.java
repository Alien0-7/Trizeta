package org.example;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

public class ArduinoSerialPortDataListener implements SerialPortDataListener {
    private final SerialPort serialPort;
    private String res;
    private volatile boolean isResFinished;

    ArduinoSerialPortDataListener(SerialPort serialPort) {
        this.serialPort = serialPort;
        this.res = "";
        this.isResFinished = false;
    }

    @Override
    public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; }
    @Override
    public void serialEvent(SerialPortEvent event) {
        if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
            return;

        byte[] newData = new byte[serialPort.bytesAvailable()];
        int numRead = serialPort.readBytes(newData, newData.length);

        for (byte b : newData) {
            if (b == '\n') {
                isResFinished = true;
            } else {
                res += (char) b;
            }
        }

    }

    public boolean isResFinished() {
        return isResFinished;
    }

    public String getRes() {
        String temp = res;
        res = "";
        isResFinished = false;
        return temp;
    }


}
