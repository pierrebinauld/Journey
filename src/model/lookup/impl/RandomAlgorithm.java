package model.lookup.impl;

import model.data.City;
import model.lookup.AbstractBuilderAlgorithm;

import java.util.ArrayList;

public class RandomAlgorithm extends AbstractBuilderAlgorithm {

	public RandomAlgorithm(ArrayList<City> cities) {
		super(cities);
	}

	@Override
	public void run() {
		Circuit c = newCircuit();
		c.setCities(cities);

		//TODO
	}
}
