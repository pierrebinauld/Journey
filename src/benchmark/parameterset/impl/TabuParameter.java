package benchmark.parameterset.impl;

import model.lookup.Circuit;
import model.service.LandscapeService;
import benchmark.parameterset.LookupParameter;

public class TabuParameter implements LookupParameter {
	private Circuit initialCircuit;
	private LandscapeService landscapeService;
	private int tabuSize;
	private int iterationCount;

	public TabuParameter(Circuit initialCircuit, LandscapeService landscapeService, int tabuSize, int iterationCount) {
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
