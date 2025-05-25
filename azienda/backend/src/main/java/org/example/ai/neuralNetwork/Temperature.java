package org.example.ai.neuralNetwork;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Temperature {

	public static final int MAX_TIME = 60 * 24, MAX_TEMP = 40, MIN_TEMP = 10;

	private int time; //minutes
	private float value;
	
	public Temperature(int time, float value) {
		this.time = time;
		this.value = value;
	}

	public Temperature(String datetime, float value) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime localDateTime = LocalDateTime.parse(datetime, formatter);

		this.time = generateTimeFromDateTime(localDateTime);
		this.value = value;
	}

	private int generateTimeFromDateTime(LocalDateTime dateTime) {
		return dateTime.getHour() * 60 + dateTime.getMinute();
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public Float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}
	
}
