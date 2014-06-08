package benchmark.parameter;

import model.iterator.key.Key;
import model.lookup.Circuit;
import model.service.LandscapeService;

import java.util.ArrayList;

public class ModifierParameter<K extends Key> implements LookupParameter {

	protected Circuit initialCircuit;
	protected LandscapeService<K> landscapeService;

	protected ModifierParameter(Circuit initialCircuit, LandscapeService<K> landscapeService) {
		this.initialCircuit = initialCircuit;
		this.landscapeService = landscapeService;
	}

	public Circuit getInitialCircuit() {
		return initialCircuit;
	}

	public LandscapeService<K> getLandscapeService() {
		return landscapeService;
	}

	@Override
	public ArrayList<String> titlesToStringArrayList() {
		ArrayList<String> result = new ArrayList<>();
		result.add("Longueur circuit initial");

		return result;
	}

	@Override
	public ArrayList<String> toStringArrayList() {
		ArrayList<String> result = new ArrayList<>();
		result.add(Integer.toString(getInitialCircuit().getLength()));

		return result;
	}
}
