package org.example.ai.neuralNetwork;

import org.example.ai.classification.Panel;

public enum InputType {

	CLASSIFICATION(0,Panel.DIMENSION),
	TEMPERATURE(-100,100),
	CO2(-3000,3000),
	HUMIDITY(-200,200);
	
	private float minValue, maxValue; //unit: amount that defines how many units are in a line of the graph
	
	InputType(float minValue, float maxValue) {
		this.minValue = minValue;
		this.maxValue = maxValue;
	}
	
	public float getMinValue() {
		return minValue;
	}
	
	public float getMaxValue() {
		return maxValue;
	}

	public float getDelta() {
		return getMaxValue() - getMinValue();
	}
}
