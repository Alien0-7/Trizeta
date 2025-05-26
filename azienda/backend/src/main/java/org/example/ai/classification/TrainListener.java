package org.example.ai.classification;

import java.util.ArrayList;

import org.example.ai.common.Point;

public interface TrainListener {

	public void train();
	public ArrayList<Point> getInputs();

}
