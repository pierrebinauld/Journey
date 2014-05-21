package model.iterator.landscape;

import java.util.List;

import model.data.City;
import model.iterator.LandscapeIterator;
import model.service.DistanceService;

public abstract class AbstractLandscapeIterator<Key> implements LandscapeIterator<Key> {

	protected DistanceService distanceService;
	protected List<City> cities;
	
	public AbstractLandscapeIterator(DistanceService distanceService, List<City> cities) {
		this.distanceService = distanceService;
		this.cities = cities;
	}

}
