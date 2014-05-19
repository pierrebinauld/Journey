package model.lookup;

import java.util.List;

import model.data.City;
import model.service.DistanceService;

public abstract class AbstractBuilderAlgorithm implements Lookup {

	protected List<City> cities;
	protected DistanceService distanceService;

	protected AbstractBuilderAlgorithm(DistanceService distanceService, List<City> cities) {
		this.distanceService = distanceService;
		this.cities = cities;
	}
}
