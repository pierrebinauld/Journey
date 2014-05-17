package model.service.impl;

import model.data.City;
import model.service.DistanceService;

import java.util.List;

public abstract class AbstractDistanceService implements DistanceService {

	protected List<City> cities;

	public AbstractDistanceService(List<City> cities) {
		this.cities = cities;
	}

	public List<City> getCities() {
		return cities;
	}
}
