package benchmark.parameterset.impl;

import benchmark.parameterset.LookupParameter;
import model.lookup.Circuit;
import model.service.LandscapeService;

import java.util.ArrayList;

public class SimulatedAnnealingParameter implements LookupParameter {
	private Circuit initialCircuit;
	private LandscapeService landscapeService;
	private double temperature;
	private double lambda;
	private double temperatureBreakpoint;

	public SimulatedAnnealingParameter(Circuit initialCircuit, LandscapeService landscapeService, double temperature, double lambda, double temperatureBreakpoint) {
		this.initialCircuit = initialCircuit;
		this.landscapeService = landscapeService;
		this.temperature = temperature;
		this.lambda = lambda;
		this.temperatureBreakpoint = temperatureBreakpoint;
	}

	@Override
	public ArrayList<String> titlesToStringArrayList() {
		ArrayList<String> result = new ArrayList<>();

		result.add("Température");
		result.add("Lambda");
		result.add("Température d'arrêt");
		result.add("Longueur circuit initial");
		
		return result;
	}
	
	@Override
	public ArrayList<String> toStringArrayList() {
		ArrayList<String> result = new ArrayList<>();

		result.add(Double.toString(getTemperature()));
		result.add(Double.toString(getLambda()));
		result.add(Double.toString(getTemperatureBreakpoint()));
		result.add(Integer.toString(getInitialCircuit().getLength()));
		
		return result;
	}
	public Circuit getInitialCircuit() {
		return initialCircuit;
	}

	public LandscapeService getLandscapeService() {
		return landscapeService;
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
