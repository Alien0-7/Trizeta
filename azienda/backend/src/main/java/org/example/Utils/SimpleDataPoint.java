package org.example.Utils;

public class SimpleDataPoint {
    private String timeStr;
    private double value;

    public SimpleDataPoint(String timeStr, double value) {
        this.timeStr = timeStr;
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public String getTimeStr() {
        return timeStr;
    }

}
