package model.lookup;

import model.data.City;
import model.service.DistanceService;

import java.util.List;

public abstract class AbstractBuilderAlgorithm implements Lookup {

	protected List<City> cities;
	protected DistanceService distanceService;

	protected AbstractBuilderAlgorithm(DistanceService distanceService, List<City> cities) {
		this.distanceService = distanceService;
		this.cities = cities;
	}
}
