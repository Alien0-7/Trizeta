package org.example.ai;

import java.util.ArrayList;

import org.example.ai.neuralNetwork.*;
import org.example.Utils.Measurement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AI {
	private static final Logger log = LoggerFactory.getLogger(AI.class);
	private int GRANULARITY = 10, IN = 1, OUT = 1;;
	private ArrayList<Measurement> temps;
	private ArrayList<Measurement> predictedTemps;
	private final int EPOCHS = 5000;
	private String FILE_SAVE = "src/main/java/org/example/ai/save", FILE_EXTENSION = ".dat";
	private NeuralNetwork nn;


	public AI(ArrayList<Measurement> temps) {
		this.temps = temps;
//		log.info("tutte le temperature");
//		for (Measurement t: temps)
//			System.out.print("t:"+t.getTime() + ", v:" + t.getValue()+ "| ");

		predictedTemps = new ArrayList<>();

		try {
			nn = NeuralNetwork.load(FILE_SAVE + FILE_EXTENSION);

		} catch (Exception e) {
//			log.info("Errore nel caricare il file dei pesi dell'AI, forse non c'era o non era nel path corretto");
//			log.info("L'AI verrà allenata adesso");
			nn = NeuralNetworkBuilder.Builder()
					.input(IN)
					.hidden(5, ActivationFunctionType.GELU)
					.hidden(4, ActivationFunctionType.GELU)
					.hidden(3, ActivationFunctionType.GELU)
					.hidden(3, ActivationFunctionType.GELU)
					.output(OUT, ActivationFunctionType.SIGMOID)
					.build();

			train();

			nn.save(FILE_SAVE + FILE_EXTENSION);
		}


//		log.info("tutte le temps pred:");
//		for (Measurement t: predictedTemps)
//			System.out.print("t:"+t.getTime() + ", v:" + t.getValue()+ "| ");


		// per una temperatura specifica
//		Input inTime = new Input(0, InputType.CLASSIFICATION);
//
//		Float[] out = null;
//		try {
//			out = nn.evaluate(inTime);
//		} catch (NeuralNetworkException e) {
//			e.printStackTrace();
//		}


	}


	public void train() {
		int N = EPOCHS;
		//long startTime = System.currentTimeMillis();

		//training 
		for(int k=0;k<N;k++) {
			Input[] in = new Input[IN];
			Float[] out = new Float[OUT];

			for (int j=0;j<temps.size();j++) {
				in[0] = new Input(temps.get(j).getTime(), InputType.CLASSIFICATION);

				out[0] = (temps.get(j).getValue()- Measurement.MIN_TEMP)/(Measurement.MAX_TEMP - Measurement.MIN_TEMP);

				try {
					nn.test(in, out);
				} catch (NeuralNetworkException e) {
					e.printStackTrace();
				}
			}

		}
	}
	public ArrayList<Measurement> evaluate() {
		//evaluating
		for (int time = 0; time< Measurement.MAX_TIME; time+=GRANULARITY) {
			Input inTime = new Input(time, InputType.CLASSIFICATION);

			Float[] out = null;
			try {
				out = nn.evaluate(inTime);
			} catch (NeuralNetworkException e) {
				e.printStackTrace();
			}

			predictedTemps.add(new Measurement(time, (out[0]*(Measurement.MAX_TEMP- Measurement.MIN_TEMP)) + Measurement.MIN_TEMP));
		}

		return predictedTemps;
	}

}
