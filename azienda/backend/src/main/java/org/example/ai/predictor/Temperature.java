package org.example.ai.predictor;

import org.example.ai.neuralNetwork.InputType;

public class Temperature extends DataPoint {
	
	public Temperature(int time, float value) {
		super(InputType.TEMPERATURE, VisualizationType.TEMPERATURE, time, value);
	}
	
}
