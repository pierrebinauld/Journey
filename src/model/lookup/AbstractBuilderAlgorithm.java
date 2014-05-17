package model.lookup;

import model.data.City;

import java.util.List;

public abstract class AbstractBuilderAlgorithm implements Lookup {

	protected List<City> cities;

	protected AbstractBuilderAlgorithm(List<City> cities) {
		this.cities = cities;
	}
}
