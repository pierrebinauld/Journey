package benchmark.parameter.impl;

import java.util.ArrayList;

import model.lookup.Circuit;
import model.service.LandscapeService;
import benchmark.parameter.LookupParameter;

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
	
	@Override
	public ArrayList<String> titlesToStringArrayList() {
		ArrayList<String> result = new ArrayList<>();

		result.add("Nb itérations");
		result.add("Taille liste Tabou");
		result.add("Longueur circuit initial");
		
		return result;
	}
	
	@Override
	public ArrayList<String> toStringArrayList() {
		ArrayList<String> result = new ArrayList<>();

		result.add(Integer.toString(getIterationCount()));
		result.add(Integer.toString(getTabuSize()));
		result.add(Integer.toString(getInitialCircuit().getLength()));
		
		return result;
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
