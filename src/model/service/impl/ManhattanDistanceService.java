package model.service.impl;

import java.util.List;

import model.data.City;
import model.lookup.Circuit;

public class ManhattanDistanceService extends AbstractDistanceService {
	
	public ManhattanDistanceService(List<City> cities) {
		super(cities);
	}

	@Override
	public int getDistance(int indexCity1, int indexCity2) {
		return ManhattanDistanceService.manhattan(cities.get(indexCity1), cities.get(indexCity2));
	}
	
	@Override
	public boolean checkLength(Circuit circuit) {
		// TODO Auto-generated method stub
		return false;
	}

	public static int manhattan(City city1, City city2) {
		return (int) Math.round(
				Math.abs(city1.getPosition().getX() - city2.getPosition().getX()) +
				Math.abs(city1.getPosition().getY() - city2.getPosition().getY())
				);
	}

}
