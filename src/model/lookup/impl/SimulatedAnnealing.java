package model.lookup.impl;

import model.lookup.AbstractModifierAlgorithm;
import model.lookup.Circuit;
import model.service.LandscapeService;

public class SimulatedAnnealing<Key> extends AbstractModifierAlgorithm<Key> {

	private double temperature;
	private double temperatureBreakpoint;
	private double lambda;

	public SimulatedAnnealing(LandscapeService<Key> landscapeService, Circuit initialCircuit, double temperature, double lambda, double temperatureBreakpoint) {
		super(landscapeService, initialCircuit);

		this.temperature = temperature;
		this.temperatureBreakpoint = temperatureBreakpoint;
		
		if (lambda >= 1) {
			lambda = lambda - Math.floor(lambda);
		}
		this.lambda = lambda;
	}

	@Override
	public Circuit run() {
		Circuit result = initialCircuit;
		int resultLength = initialCircuit.getLength();
		
		int length;
		int currentLength = initialCircuit.getLength();

		while(temperature > temperatureBreakpoint) {
			Key key = landscapeService.randomKey();
			
			length = landscapeService.getNeighborLength(key);
			if(length < currentLength || metropolisRule(length, currentLength)) {
				Circuit circuit = landscapeService.getNeighbor(key);
				currentLength = circuit.getLength();
				landscapeService.setCircuit(circuit);
				
				if(currentLength < resultLength) {
					result = circuit;
					resultLength = currentLength;
				}
			}

			temperature *= lambda;
			System.out.println(temperature);
		}

		return result;

	}

	private boolean metropolisRule(int testedLength, int currentLength) {
		return Math.random() < Math.exp(-(testedLength - currentLength)/temperature);
	}
}
