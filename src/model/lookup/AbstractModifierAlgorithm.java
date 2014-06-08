package model.lookup;

import benchmark.parameter.ModifierParameter;
import model.data.City;
import model.iterator.key.Key;
import model.service.LandscapeService;

import java.util.List;

public abstract class AbstractModifierAlgorithm<K extends Key, P extends ModifierParameter<K>> extends Lookup<P> {

	protected LandscapeService<K> landscapeService;
	
	protected Circuit initialCircuit;
	protected List<City> cities;

	public AbstractModifierAlgorithm(P parameter) {
		super(parameter);
		landscapeService = parameter.getLandscapeService();
		initialCircuit = parameter.getInitialCircuit();

		cities = initialCircuit.getCities();
		landscapeService.setCircuit(initialCircuit);
	}
}
