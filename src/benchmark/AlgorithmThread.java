package benchmark;

import benchmark.parameterset.LookupParameter;
import model.lookup.Circuit;
import model.lookup.Lookup;

public class AlgorithmThread<P extends LookupParameter> extends Thread {

	private Lookup algorithm;
	private P parameter;
	private Circuit result;

	public AlgorithmThread(P parameter, Lookup algorithm) {
		this.algorithm = algorithm;
		this.parameter = parameter;
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

	public P getParameter() {
		return parameter;
	}

	public void setParameter(P parameter) {
		this.parameter = parameter;
	}
	
	
}
