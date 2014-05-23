package model.lookup;

import java.util.List;

import model.data.City;
import model.service.LandscapeService;

public abstract class AbstractModifierAlgorithm<Key> implements Lookup {

	protected LandscapeService<Key> landscapeService;
	
	protected Circuit circuit;
	protected List<City> cities;

	public AbstractModifierAlgorithm(LandscapeService<Key> landscapeService, Circuit initialCircuit) {
		this.landscapeService = landscapeService;
		
		this.circuit = initialCircuit;
		this.cities = circuit.getCities();
	}
}
