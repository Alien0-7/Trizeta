package org.example.ai.neuralNetwork;

import org.example.ai.classification.Panel;

public enum InputType {

	CLASSIFICATION(0,Panel.DIMENSION),
	XOR(0,1);
	
	private float minValue, maxValue;
	
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
}
