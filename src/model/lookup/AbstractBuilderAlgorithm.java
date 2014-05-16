package model.lookup;

import model.data.City;

import java.util.ArrayList;

public abstract class AbstractBuilderAlgorithm implements Lookup {

	private ArrayList<City> cities;

	protected AbstractBuilderAlgorithm(ArrayList<City> cities) {
		this.cities = cities;
	}
}
