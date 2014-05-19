package model.lookup.impl;

import java.util.ArrayList;

import model.data.City;
import model.lookup.AbstractBuilderAlgorithm;
import model.lookup.Circuit;
import model.service.DistanceService;

public class RandomAlgorithm extends AbstractBuilderAlgorithm {

	public RandomAlgorithm(DistanceService distanceService, ArrayList<City> cities) {
		super(distanceService, cities);
	}

	@Override
	public Circuit run() {
		//TODO: implement algorithm
		return null;
	}
}
