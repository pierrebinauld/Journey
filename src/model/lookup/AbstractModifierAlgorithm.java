package model.lookup;

import java.util.List;

import model.data.City;
import model.iterator.LandscapeIterator;

public abstract class AbstractModifierAlgorithm implements Lookup {

	protected LandscapeIterator<?> landscapeIterator;
	
	protected Circuit circuit;
	protected List<City> cities;

	public AbstractModifierAlgorithm(LandscapeIterator<?> landscapeIterator, Circuit initialCircuit) {
		this.landscapeIterator = landscapeIterator;
		
		this.circuit = initialCircuit;
		this.cities = circuit.getCities();
	}
}
