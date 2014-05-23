package model.lookup.impl;

import model.lookup.AbstractModifierAlgorithm;
import model.lookup.Circuit;
import model.service.LandscapeService;

public class SimulatedAnnealing<Key> extends AbstractModifierAlgorithm<Key> {

	protected SimulatedAnnealing(LandscapeService<Key> landscapeService, Circuit initialCircuit) {
		super(landscapeService, initialCircuit);
	}

	@Override
	public Circuit run() {
		//TODO: implement algorithm
		return null;
	}
}
