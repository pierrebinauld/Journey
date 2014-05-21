package model.lookup.impl;

import model.iterator.LandscapeIterator;
import model.lookup.AbstractModifierAlgorithm;
import model.lookup.Circuit;

public class SimulatedAnnealing extends AbstractModifierAlgorithm {

	protected SimulatedAnnealing(LandscapeIterator<?> landscapeIterator, Circuit initialCircuit) {
		super(landscapeIterator, initialCircuit);
	}

	@Override
	public Circuit run() {
		//TODO: implement algorithm
		return null;
	}
}
