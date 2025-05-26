package org.example.ai.predictor;


import org.example.ai.neuralNetwork.InputType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataPoint {

	public static final int MAX_TIME = 60 * 24; // in minutes

	private InputType inputType;
	private VisualizationType visualizationType;
	private int timeInt; //in minutes
	private String timeStr;
	private float value;


	
	public DataPoint(InputType inputType, VisualizationType visualizationType, int timeInt, float value) {
		this.inputType = inputType;
		this.visualizationType = visualizationType;
		this.timeInt = timeInt;
//		this.timeStr = generateTimeFromTimeInt(this.timeInt);
		this.value = value;
	}

	public DataPoint(InputType inputType, VisualizationType visualizationType, String timeStr, float value) {
		this.inputType = inputType;
		this.visualizationType = visualizationType;
		this.timeStr = timeStr;
		this.timeInt = generateTimeFromTimeString(this.timeStr);
		this.value = value;
	}

	private int generateTimeFromTimeString(String datetime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime localDateTime = LocalDateTime.parse(datetime, formatter);

		return localDateTime.getHour() * 60 + localDateTime.getMinute();
	}

//	private String generateTimeFromTimeInt(int time) {
//		return "";
//	}

	public int getTimeInt() {
		return timeInt;
	}

	public void setTimeInt(int timeInt) {
		this.timeInt = timeInt;
	}

	public InputType getInputType() {
		return inputType;
	}

	public VisualizationType getVisualizationType() {
		return visualizationType;
	}
	
	public Float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public String getTimeStr() {
		return timeStr;
	}
}
