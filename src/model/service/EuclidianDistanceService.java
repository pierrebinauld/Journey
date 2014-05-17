package model.service;

import java.util.List;

import model.data.City;
import model.lookup.Circuit;
import model.service.impl.AbstractDistanceService;

public class EuclidianDistanceService extends AbstractDistanceService {

	public EuclidianDistanceService(List<City> cities) {
		super(cities);
	}

	@Override
	public int getDistance(int indexCity1, int indexCity2) {
		return EuclidianDistanceService.Euclidian(cities.get(indexCity1), cities.get(indexCity2));
	}

	@Override
	public boolean checkLength(Circuit circuit) {
		// TODO Auto-generated method stub
		return false;
	}

	public static int Euclidian(City city1, City city2) {
		return 	(int) Math.round(city1.getPosition().distance(city2.getPosition()));
	}
}
