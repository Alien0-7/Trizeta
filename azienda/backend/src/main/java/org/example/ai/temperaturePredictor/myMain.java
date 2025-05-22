package org.example.ai.temperaturePredictor;

import java.util.ArrayList;

import org.example.ai.common.RandomSingleton;

public class myMain {

	public static void main(String[] args) {
		new AI(generateRandom());
	}

	public static ArrayList<Temperature> readFromDB(String UUID) {
		ArrayList<Temperature> results = new ArrayList<>();
		//TODO read from DB

		return results;
	}
	
	public static ArrayList<Temperature> generateRandom() {
		ArrayList<Temperature> results = new ArrayList<>();
		results.add(new Temperature(0, RandomSingleton.randFloat(10, 30)));

		for(int i=1;i<24*60;i++) {
			results.add(new Temperature(i,
					results.get(i-1).getValue()
					+ RandomSingleton.randFloat(0, 0.4f)
					+ RandomSingleton.randFloat(0, 0.1f)
					- RandomSingleton.randFloat(0, 0.4f)
					- RandomSingleton.randFloat(0, 0.1f)));
		}

		return results;
	}
}
