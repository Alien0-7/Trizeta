package org.example.ai.temperaturePredictor;

import java.util.ArrayList;

import org.example.ai.neuralNetwork.*;

public class AI {

	private int GRANULARITY = 10, IN = 1, OUT = 1;;
	private ArrayList<Temperature> temps;
	private ArrayList<Temperature> predictedTemps;
	private final int EPOCHS = 5000;
	private String FILE_SAVE = "save", FILE_EXTENSION = ".dat";
	private NeuralNetwork nn;


	public AI(ArrayList<Temperature> temps) {
		this.temps = temps;
		System.out.println("tutte le temperature");
		for (Temperature t: temps)
			System.out.print("t:"+t.getTime() + ", v:" + t.getValue()+ "| ");

		predictedTemps = new ArrayList<>();

		try {
			nn = NeuralNetwork.load(FILE_SAVE + FILE_EXTENSION);

		} catch (Exception e) {
			System.out.println("Errore nel caricare il file dei pesi dell'AI, forse non c'era o non era nel path corretto");
			System.out.println("L'AI verrà allenata adesso");
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

		evaluate();


		System.out.println("tutte le temps pred:");
		for (Temperature t: predictedTemps)
			System.out.print("t:"+t.getTime() + ", v:" + t.getValue()+ "| ");


		// per temperatura specifica
		Input inTime = new Input(0, InputType.CLASSIFICATION);

		Float[] out = null;
		try {
			out = nn.evaluate(inTime);
		} catch (NeuralNetworkException e) {
			e.printStackTrace();
		}


	}


	public void train() {
		int N = EPOCHS;
		long startTime = System.currentTimeMillis();

		//training 
		for(int k=0;k<N;k++) {
			Input[] in = new Input[IN];
			Float[] out = new Float[OUT];

			for (int j=0;j<temps.size();j++) {
				in[0] = new Input(temps.get(j).getTime(), InputType.CLASSIFICATION);

				out[0] = (temps.get(j).getValue()-Temperature.MIN_TEMP)/(Temperature.MAX_TEMP-Temperature.MIN_TEMP);

				try {
					nn.test(in, out);
				} catch (NeuralNetworkException e) {
					e.printStackTrace();
				}
			}

			if (k%100 == 0) {
				printProgressBar(startTime, k, N);
			}
		}
		System.out.println();

	}
	public void evaluate() {
		//evaluating
		for (int time=0;time<Temperature.MAX_TIME;time+=GRANULARITY) {
			Input inTime = new Input(time, InputType.CLASSIFICATION);

			Float[] out = null;
			try {
				out = nn.evaluate(inTime);
			} catch (NeuralNetworkException e) {
				e.printStackTrace();
			}

			predictedTemps.add(new Temperature(time, (out[0]*(Temperature.MAX_TEMP-Temperature.MIN_TEMP)) + Temperature.MIN_TEMP));
		}

	}

	private void printProgressBar(long startTime, int i, long total) {
		clearLine();
		printPB((int) ((i * 100.0) / total), ((System.currentTimeMillis() - startTime) * total) / (i + 1) - (System.currentTimeMillis() - startTime));

	}

	private void printPB(int percent, long remainingTimeMillis) {
		int barLength = 50;
		int filledLength = (percent * barLength) / 100;

		StringBuilder bar = new StringBuilder();
		bar.append("[");
		for (int j = 0; j < barLength; j++) {
			if (j < filledLength) {
				bar.append("=");
			} else {
				bar.append(" ");
			}
		}
		bar.append("] ");

		System.out.print("\r" + bar + percent + "% - Remaining time: " + remainingTimeMillis / 1000 + "s");
	}

	private static void clearLine() {
		System.out.print("\033[F");  // Move cursor up one line
		System.out.print("\033[2K"); // Clear the entire line
	}


}
