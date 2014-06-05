package benchmark;

import benchmark.parameter.LookupParameter;
import model.lookup.Circuit;
import model.lookup.Lookup;
import model.service.TimeService;

public class AlgorithmThread<P extends LookupParameter> extends Thread {

	private Lookup algorithm;
	private P parameter;
	private Circuit result;
	private float executionTime;
	
	private TimeService time = new TimeService();

	public AlgorithmThread(P parameter, Lookup algorithm) {
		this.algorithm = algorithm;
		this.parameter = parameter;
	}

	@Override
	public void run() {
		super.run();
		time.start();
		result = algorithm.run();
		executionTime = time.tickInSecond();
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

	public float getExecutionTime() {
		return executionTime;
	}
	
	
}
