package benchmark;

import benchmark.parameter.LookupParameter;
import model.lookup.Lookup;
import model.service.TimeService;

import java.util.concurrent.Callable;

public class AlgorithmThread<P extends LookupParameter> implements Callable<ExecutionResult<P>> {

	private Lookup algorithm;
	private ExecutionResult<P> result = new ExecutionResult<>();
	
	private TimeService time = new TimeService();

	public AlgorithmThread(P parameter, Lookup algorithm) {
		this.algorithm = algorithm;
		result.setParameter(parameter);
	}

	@Override
	public ExecutionResult<P> call() throws Exception {
		time.start();
		result.setCircuit(algorithm.run());
		result.setExecutionTime(time.tickInSecond());

		return result;
	}
}
