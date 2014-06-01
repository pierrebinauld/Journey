package benchmark.parameterset.impl;

import benchmark.parameterset.ParameterSet;
import model.lookup.Circuit;
import model.service.LandscapeService;

public class TabuParameterSet implements ParameterSet {
	private Circuit initialCircuit;
	private LandscapeService landscapeService;
	private int tabuSize;
	private int iterationCount;

	public TabuParameterSet(Circuit initialCircuit, LandscapeService landscapeService, int tabuSize, int iterationCount) {
		this.initialCircuit = initialCircuit;
		this.landscapeService = landscapeService;
		this.tabuSize = tabuSize;
		this.iterationCount = iterationCount;
	}

	public Circuit getInitialCircuit() {
		return initialCircuit;
	}

	public LandscapeService getLandscapeService() {
		return landscapeService;
	}

	public int getTabuSize() {
		return tabuSize;
	}

	public int getIterationCount() {
		return iterationCount;
	}
}
