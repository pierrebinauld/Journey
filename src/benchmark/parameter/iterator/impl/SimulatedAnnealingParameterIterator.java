package benchmark.parameter.iterator.impl;

import benchmark.parameter.impl.SimulatedAnnealingParameter;
import benchmark.parameter.iterator.LookupParameterIterator;
import benchmark.parameter.set.impl.SimulatedAnnealingParameterSet;

public class SimulatedAnnealingParameterIterator implements LookupParameterIterator<SimulatedAnnealingParameter> {

	private SimulatedAnnealingParameterSet parameterSet;
	private int iTemperature;
	private int iLambda;
	private int iTemperatureBreakpoint;
	private boolean hasNext;

	public SimulatedAnnealingParameterIterator(SimulatedAnnealingParameterSet parameterSet) {
		this.parameterSet = parameterSet;

		this.hasNext = true;
	}

	@Override
	public boolean hasNext() {
		return this.hasNext;
	}

	@Override
	public SimulatedAnnealingParameter next() {
		SimulatedAnnealingParameter simulatedAnnealingParameter = new SimulatedAnnealingParameter(
				parameterSet.getInitialCircuitBuilder().run(),
				parameterSet.getLandscapeService(),
				parameterSet.getTemperature(iTemperature),
				parameterSet.getLambda(iLambda),
				parameterSet.getTemperatureBreakpoint(iTemperatureBreakpoint));

		iTemperature++;
		if(iTemperature == parameterSet.getTemperatureLength()) { // end of the 1st loop
			iLambda++;
			if(iLambda == parameterSet.getLambdaLength()) { // end of the 2nd loop
				iTemperatureBreakpoint++;
				if(iTemperatureBreakpoint == parameterSet.getTemperatureBreakpointLength()) { // end of the 3rd and last loop
					hasNext = false;
				} else {
					iTemperature = 0;
					iLambda = 0;
				}
			} else {
				iTemperature = 0;
			}
		}

		return simulatedAnnealingParameter;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
