package org.example.Utils;

public class Measurement {
    private String time; //minutes
    private double value;

    public Measurement(String datetime, double value) {
        this.time = datetime;
        this.value = value;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
