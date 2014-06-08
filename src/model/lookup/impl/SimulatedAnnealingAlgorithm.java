package model.lookup.impl;

import benchmark.parameter.impl.SimulatedAnnealingParameter;
import model.iterator.key.Key;
import model.lookup.AbstractModifierAlgorithm;
import model.lookup.Circuit;

public class SimulatedAnnealingAlgorithm<K extends Key> extends AbstractModifierAlgorithm<K, SimulatedAnnealingParameter<K>> {

	private double temperature;
	private double temperatureBreakpoint;
	private double lambda;

	public SimulatedAnnealingAlgorithm(SimulatedAnnealingParameter<K> parameter) {
		super(parameter);

		this.temperature = parameter.getTemperature();
		this.temperatureBreakpoint = parameter.getTemperatureBreakpoint();
		
		if (parameter.getLambda() >= 1) { // TODO: what is it supposed to do ???
			this.lambda = parameter.getLambda() - Math.floor(parameter.getLambda());
		} else {
			this.lambda = parameter.getLambda();
		}
	}

	@Override
	public Circuit run() {
		Circuit result = initialCircuit;
		int resultLength = initialCircuit.getLength();
		
		int length;
		int currentLength = initialCircuit.getLength();

		while(temperature > temperatureBreakpoint) {
			K key = landscapeService.randomKey();
			
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
		}

		return result;
	}

	private boolean metropolisRule(int testedLength, int currentLength) {
		return Math.random() < Math.exp(-(testedLength - currentLength)/temperature);
	}
}
