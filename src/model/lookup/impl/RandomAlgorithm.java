package model.lookup.impl;

import model.data.City;
import model.lookup.AbstractBuilderAlgorithm;
import model.lookup.Circuit;
import model.service.DistanceService;

import java.util.List;

public class RandomAlgorithm extends AbstractBuilderAlgorithm {

	public RandomAlgorithm(DistanceService distanceService, List<City> cities) {
		super(distanceService, cities);
	}

	@Override
	public Circuit run() {
		//TODO: implement algorithm
		return null;
	}
}
