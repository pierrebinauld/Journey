package benchmark.parameterset.builder.impl;

import benchmark.parameterset.builder.AbstractModifierParameterSet;
import benchmark.parameterset.impl.SimulatedAnnealingParameter;
import benchmark.parameterset.iterator.impl.SimulatedAnnealingParameterIterator;
import model.lookup.AbstractBuilderAlgorithm;
import model.service.LandscapeService;

import java.util.Iterator;

public class SimulatedAnnealingParameterSet extends AbstractModifierParameterSet<SimulatedAnnealingParameter> {
	private double[] temperature;
	private double[] lambda;
	private double[] temperatureBreakpoint;

	public SimulatedAnnealingParameterSet(AbstractBuilderAlgorithm initialCircuitBuilder, LandscapeService landscapeService, double[] temperature, double[] lambda, double[] temperatureBreakpoint) {
		super(initialCircuitBuilder, landscapeService);
		this.temperature = temperature;
		this.lambda = lambda;
		this.temperatureBreakpoint = temperatureBreakpoint;
	}

	public int getTemperatureLength() {
		return temperature.length;
	}

	public double getTemperature(int index) {
		return temperature[index];
	}

	public int getLambdaLength() {
		return lambda.length;
	}

	public double getLambda(int index) {
		return lambda[index];
	}

	public int getTemperatureBreakpointLength() {
		return temperatureBreakpoint.length;
	}

	public double getTemperatureBreakpoint(int index) {
		return temperatureBreakpoint[index];
	}

	@Override
	public Iterator<SimulatedAnnealingParameter> iterator() {
		return new SimulatedAnnealingParameterIterator(this);
	}
}
