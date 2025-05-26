package org.example.ai;

import java.util.ArrayList;

import org.example.Utils.SimpleDataPoint;
import org.example.ai.neuralNetwork.*;
import org.example.ai.predictor.DataPoint;
import org.example.ai.predictor.VisualizationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AI {
	private static final Logger log = LoggerFactory.getLogger(AI.class);
	public final static int GRANULARITY=10, EVALUATION_INTERVAL = 100, EPOCHS = 10000;

	private ArrayList<DataPoint> dataValues;
	private ArrayList<DataPoint> predictedData;
	private float mae, mse;

	private String FILE_SAVE = "src/main/java/org/example/ai/save", FILE_EXTENSION = ".dat";

	private InputType inputType;
	private VisualizationType visualizationType;
	NeuralNetwork nn;
	int IN = 1, OUT = 1;


	public AI(ArrayList<DataPoint> dataValues) {
		this.dataValues = dataValues;
		predictedData = new ArrayList<>();

		inputType = dataValues.get(0).getInputType();
		visualizationType = dataValues.get(0).getVisualizationType();

		nn = NeuralNetwork.load(FILE_SAVE + FILE_EXTENSION);

		if(nn == null) {
			NeuralNetworkSettings.setUseDropout(true);
			NeuralNetworkSettings.setDropoutRate(0.1f);
			NeuralNetworkSettings.setUseInertia(true);

			nn = NeuralNetworkBuilder.Builder()
					.input(IN)
					.hidden(2, ActivationFunctionType.GELU)
					.hidden(2, ActivationFunctionType.GELU)
					.output(OUT, ActivationFunctionType.SIGMOID)
					.build();

			train();

			feedForward();

		}

//		try {
//			nn = NeuralNetwork.load(FILE_SAVE + FILE_EXTENSION);
//
//		} catch (Exception e) {
//			nn = NeuralNetworkBuilder.Builder()
//					.input(IN)
//					.hidden(5, ActivationFunctionType.GELU)
//					.hidden(4, ActivationFunctionType.GELU)
//					.hidden(3, ActivationFunctionType.GELU)
//					.hidden(3, ActivationFunctionType.GELU)
//					.output(OUT, ActivationFunctionType.SIGMOID)
//					.build();
//
//			train();
//
//			nn.save(FILE_SAVE + FILE_EXTENSION);
//		}

	}


	public void train() {
		for (int k=0;k<EPOCHS;k++) {
			Input[] in = new Input[IN];
			Float[] out = new Float[OUT];

			for (int j=0;j<dataValues.size();j++) {
				in[0] = new Input(dataValues.get(j).getTimeInt(), InputType.CLASSIFICATION);
				out[0] = Input.normalize(dataValues.get(j).getValue(), inputType);

				try {
					nn.train(in, out);
				} catch (NeuralNetworkException e) {
					e.printStackTrace();
				}
			}

			if (k%EVALUATION_INTERVAL == 0 && k!=0) {
				feedForward();
			}
		}

		feedForward();
	}


	public void feedForward() {
		Float[] predicted = null;

		for (int time=0;time<DataPoint.MAX_TIME;time+=GRANULARITY) {
			Input inTime = new Input(time, InputType.CLASSIFICATION);

			try {
				predicted = nn.feedForward(inTime);
			} catch (NeuralNetworkException e) {
				e.printStackTrace();
			}

			predictedData.add(new DataPoint(inputType, visualizationType, time, Input.deNormalize(predicted[0], inputType)));
		}

		Float[] actual = new Float[OUT];
		int count=0;
		for (int j=0;j<dataValues.size();j++) {
			for (int i = 0; i< predictedData.size(); i++) {
				if (dataValues.get(j).getTimeInt() == predictedData.get(i).getTimeInt()) {
					actual[0] = Input.normalize(dataValues.get(j).getValue(), inputType);
					predicted[0] = Input.normalize(predictedData.get(i).getValue(), inputType);
					mae += NeuralNetwork.calculateMAE(actual, predicted);
					mse += NeuralNetwork.calculateMSE(actual, predicted);
					count++;
				}
			}
		}

		mae /= count;
		mse /= count;
	}

	public ArrayList<SimpleDataPoint> getPredictedData() {
		ArrayList<SimpleDataPoint> simplePredictedData = new ArrayList<>();
		for (DataPoint data: predictedData)
			simplePredictedData.add(new SimpleDataPoint(data.getTimeStr(), Math.round(data.getValue() * 100.0) / 100.0));

		return simplePredictedData;
	}
}
