package benchmark.parameter.impl;

import benchmark.parameter.ModifierParameter;
import model.iterator.key.Key;
import model.lookup.Circuit;
import model.service.LandscapeService;

import java.util.ArrayList;

public class SimulatedAnnealingParameter<K extends Key> extends ModifierParameter<K> {
	private double temperature;
	private double lambda;
	private double temperatureBreakpoint;

	public SimulatedAnnealingParameter(Circuit initialCircuit, LandscapeService<K> landscapeService, double temperature, double lambda, double temperatureBreakpoint) {
		super(initialCircuit, landscapeService);
		this.temperature = temperature;
		this.lambda = lambda;
		this.temperatureBreakpoint = temperatureBreakpoint;
	}

	@Override
	public ArrayList<String> titlesToStringArrayList() {
		ArrayList<String> result = super.titlesToStringArrayList();

		result.add("Température");
		result.add("Lambda");
		result.add("Température d'arrêt");
		
		return result;
	}
	
	@Override
	public ArrayList<String> toStringArrayList() {
		ArrayList<String> result = super.toStringArrayList();

		result.add(Double.toString(getTemperature()));
		result.add(Double.toString(getLambda()));
		result.add(Double.toString(getTemperatureBreakpoint()));
		
		return result;
	}

	public double getTemperature() {
		return temperature;
	}

	public double getLambda() {
		return lambda;
	}

	public double getTemperatureBreakpoint() {
		return temperatureBreakpoint;
	}
}
