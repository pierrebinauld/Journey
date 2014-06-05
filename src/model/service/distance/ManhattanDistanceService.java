package model.service.distance;

import model.data.City;

import java.util.List;

public class ManhattanDistanceService extends AbstractDistanceService {

	public static int manhattan(City city1, City city2) {
		return (int) Math.round(
				Math.abs(city1.getPosition().getX() - city2.getPosition().getX()) +
						Math.abs(city1.getPosition().getY() - city2.getPosition().getY())
		);
	}

	public ManhattanDistanceService(List<City> cities) {
		super(cities);
	}

	@Override
	public int getDistance(int indexCity1, int indexCity2) {
		return ManhattanDistanceService.manhattan(cities.get(indexCity1), cities.get(indexCity2));
	}
}
