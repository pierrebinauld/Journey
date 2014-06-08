package benchmark;

import benchmark.parameter.LookupParameter;
import model.lookup.Circuit;

public class ExecutionResult<P extends LookupParameter> {

	private P parameter;
	private Circuit circuit;
	private float executionTime;

	public P getParameter() {
		return parameter;
	}

	public void setParameter(P parameter) {
		this.parameter = parameter;
	}

	public Circuit getCircuit() {
		return circuit;
	}

	public void setCircuit(Circuit circuit) {
		this.circuit = circuit;
	}

	public float getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(float executionTime) {
		this.executionTime = executionTime;
	}
}
