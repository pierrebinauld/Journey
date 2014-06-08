package benchmark.parameter.impl;

import benchmark.parameter.ModifierParameter;
import model.iterator.key.Key;
import model.lookup.Circuit;
import model.service.LandscapeService;

import java.util.ArrayList;

public class TabuParameter<K extends Key> extends ModifierParameter<K> {
	private int tabuSize;
	private int iterationCount;

	public TabuParameter(Circuit initialCircuit, LandscapeService<K> landscapeService, int tabuSize, int iterationCount) {
		super(initialCircuit, landscapeService);
		this.tabuSize = tabuSize;
		this.iterationCount = iterationCount;
	}
	
	@Override
	public ArrayList<String> titlesToStringArrayList() {
		ArrayList<String> result = super.titlesToStringArrayList();

		result.add("Nombre d'itérations");
		result.add("Taille liste Tabou");
		
		return result;
	}
	
	@Override
	public ArrayList<String> toStringArrayList() {
		ArrayList<String> result = super.toStringArrayList();

		result.add(Integer.toString(getIterationCount()));
		result.add(Integer.toString(getTabuSize()));
		
		return result;
	}

	public int getTabuSize() {
		return tabuSize;
	}

	public int getIterationCount() {
		return iterationCount;
	}
}
