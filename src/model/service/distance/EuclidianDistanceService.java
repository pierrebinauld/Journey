package model.service.distance;

import model.data.City;

import java.util.List;

public class EuclidianDistanceService extends AbstractDistanceService {

	public static int Euclidian(City city1, City city2) {
		return (int) Math.round(city1.getPosition().distance(city2.getPosition()));
	}
	
	public EuclidianDistanceService(List<City> cities) {
		super(cities);
	}

	@Override
	public int getDistance(int indexCity1, int indexCity2) {
		return EuclidianDistanceService.Euclidian(cities.get(indexCity1), cities.get(indexCity2));
	}
}
