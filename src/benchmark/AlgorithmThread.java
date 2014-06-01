package benchmark;

import model.lookup.Circuit;
import model.lookup.Lookup;

public class AlgorithmThread implements Runnable {

	private Lookup algorithm;
	private Circuit result;

	public AlgorithmThread(Lookup algorithm) {
		this.algorithm = algorithm;
	}

	@Override
	public void run() {
		result = algorithm.run();
		//TODO: enregistrer le resultat
	}
}
