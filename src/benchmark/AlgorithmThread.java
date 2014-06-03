package benchmark;

import model.lookup.Circuit;
import model.lookup.Lookup;

public class AlgorithmThread extends Thread {

	private Lookup algorithm;
	private Circuit result;

	public AlgorithmThread(Lookup algorithm) {
		this.algorithm = algorithm;
	}

	@Override
	public void run() {
		super.run();
		result = algorithm.run();
	}

	public Circuit getResult() {
		return result;
	}

	public void setResult(Circuit result) {
		this.result = result;
	}
	
	
}
